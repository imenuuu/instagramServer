package com.example.demo.src.Story;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.Story.model.GetStorykeepRes;
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

    public StoryController(StoryProvider storyProvider, JwtService jwtService) {
        this.storyProvider = storyProvider;
        this.jwtService = jwtService;
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

}
