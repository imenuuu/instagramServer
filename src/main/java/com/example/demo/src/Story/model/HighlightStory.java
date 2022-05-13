package com.example.demo.src.Story.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class HighlightStory {
    private Long story_id;
    @Override
    public String toString() {
        return "storyHighlight [stroy_id="+story_id+"]";
    }
}