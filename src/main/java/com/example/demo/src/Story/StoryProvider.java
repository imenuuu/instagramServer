package com.example.demo.src.Story;

import com.example.demo.config.BaseException;
import com.example.demo.src.Story.model.GetStorykeepRes;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class StoryProvider {

    private final StoryDao storyDao;

    public StoryProvider(StoryDao storyDao) {
        this.storyDao = storyDao;
    }
    public List<GetStorykeepRes> getStoryKeep(Long userIdx) throws BaseException {
        try{
            List<GetStorykeepRes> getStorykeepRes=storyDao.getStoryKeep(userIdx);
            return getStorykeepRes;
        }catch(Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
