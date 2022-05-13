package com.example.demo.src.Story.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostHighLightReq {
    private Long story_id;
    private Long highlight_id;
    private Long user_id;
}
