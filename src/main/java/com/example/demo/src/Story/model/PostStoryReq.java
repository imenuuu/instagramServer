package com.example.demo.src.Story.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostStoryReq {
    private Long user_id;
    private String storyUrl;
}
