package com.example.demo.src.Story;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.Story.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.INVALID_USER_JWT;

@RestController
@RequestMapping("/app/stories")
public class StoryController {


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
    public BaseResponse<String> createHighlights(@PathVariable("userIdx") Long userIdx ,@RequestBody PostHighLightStoryIdxReq postHighLightStoryIdxReq){
        try {
            int userIdxByJwt=jwtService.getUserIdx();
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            System.out.println(postHighLightStoryIdxReq.toString());
            PostHighlightList postHighlightList=new PostHighlightList(userIdx,postHighLightStoryIdxReq.getHighlightImg(),postHighLightStoryIdxReq.getHighlightTitle());
            Long lastInsertId=storyService.createHighlightList(postHighlightList);

            for(HighlightStory highlightstory : postHighLightStoryIdxReq.getHighlightStory()) {
                PostHighLightReq postHighLightReq = new PostHighLightReq(highlightstory.getStory_id(), lastInsertId, userIdx);
                storyService.createHighlight(postHighLightReq);
            }
            String result="";
            return new BaseResponse<>(result);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

    }

}
