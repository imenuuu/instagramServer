package com.example.demo.src.Story;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.Story.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.INVALID_USER_JWT;

@RestController
@RequestMapping("/app/stories")
public class StoryController {

    @Autowired
    DataSource dataSource;
    @Autowired
    private final StoryProvider storyProvider;

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final StoryService storyService;
    public StoryController(StoryProvider storyProvider, JwtService jwtService,StoryService storyService) {
        this.storyProvider = storyProvider;
        this.jwtService = jwtService;
        this.storyService=storyService;
    }

    @ResponseBody
    @GetMapping("/keep/{userIdx}")
    public BaseResponse<List<GetStorykeepRes>> getStories(@PathVariable("userIdx") Long userIdx){
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetStorykeepRes> getStorykeepRes=storyProvider.getStoryKeep(userIdx);
            return new BaseResponse<>(getStorykeepRes);
        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    @ResponseBody
    @PostMapping("")
    public BaseResponse<String> createStory(@RequestBody Story story){
        try {
            int userIdx=jwtService.getUserIdx();

            PostStoryReq posStoryReq=new PostStoryReq((long) userIdx,story.getStoryUrl());
            storyService.createStory(posStoryReq);

            String result="";
            return new BaseResponse<>(result);

        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @ResponseBody
    @PostMapping("/highlights/{userIdx}")
    public BaseResponse<String> createHighlights(@PathVariable("userIdx") Long userIdx ,@RequestBody PostHighLightStoryIdxReq postHighLightStoryIdxReq) throws SQLException {
        try {
            int userIdxByJwt=jwtService.getUserIdx();
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            PostHighLightList postHighlightList=new PostHighLightList(userIdx,postHighLightStoryIdxReq.getHighlightImg(),postHighLightStoryIdxReq.getHighlightTitle());
            TransactionSynchronizationManager.initSynchronization(); // 트랜잭션 동기화 작업 초기화
            Connection conn= DataSourceUtils.getConnection(dataSource);
            conn.setAutoCommit(false);
            try {
                Long lastInsertId = storyService.createHighlightList(postHighlightList);
                for (HighlightStory highlightstory : postHighLightStoryIdxReq.getHighlightStory()) {
                    PostHighLightReq postHighLightReq = new PostHighLightReq(highlightstory.getStory_id(), lastInsertId, userIdx);
                    storyService.createHighlight(postHighLightReq);
                    conn.commit();
                }
            }catch (SQLException e){
                conn.rollback();
            }finally{
                DataSourceUtils.releaseConnection(conn,dataSource);
                TransactionSynchronizationManager.unbindResource(this.dataSource);
                TransactionSynchronizationManager.clearSynchronization();
            }
            String result="";
            return new BaseResponse<>(result);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

    }

}
