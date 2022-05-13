package com.example.demo.src.Story;

import com.example.demo.config.BaseException;
import com.example.demo.src.Story.model.HighlightStory;
import com.example.demo.src.Story.model.PostHighLightReq;
import com.example.demo.src.Story.model.PostHighLightList;
import com.example.demo.src.Story.model.PostStoryReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.CREATE_FAIL_HIGHLIGHT;
import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class StoryService {
    private final StoryDao storyDao;
    private final StoryProvider storyProvider;

    @Autowired
    public StoryService(StoryDao storyDao,StoryProvider storyProvider){
        this.storyDao=storyDao;
        this.storyProvider=storyProvider;
    }

    public void createStory(PostStoryReq posStoryReq) throws BaseException {
        storyDao.createStory(posStoryReq);
    }

    public List<HighlightStory> createHighlights(HighlightStory highlightstory) {

        return null;
    }



    public Long createHighlightList(PostHighLightList postHighlightList) {
        Long highlightIdx=storyDao.createHighlightList(postHighlightList);
        return highlightIdx;
    }
    public void createHighlight(PostHighLightReq postHighLightReq) throws BaseException{
        try {
            int result = storyDao.createHighlight(postHighLightReq);
            if (result == 0) {
                throw new BaseException(CREATE_FAIL_HIGHLIGHT);
            }
        }
        catch(Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
