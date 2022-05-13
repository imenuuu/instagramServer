package com.example.demo.src.Story.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostHighLightStoryIdxReq {
    private String highlightImg;
    private String highlightTitle;
    private List<HighlightStory> highlightStory;
    public String toString() {
        return "PostHighight [hightlightImg:"+highlightImg+" highlightTitle:"+highlightTitle+" `story_id="+highlightStory+"]";
    }

}
