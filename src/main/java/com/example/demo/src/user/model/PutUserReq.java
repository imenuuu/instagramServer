package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PutUserReq {
    private Long userIdx;
    private String profileImgUrl;
    private String userName;
    private String userNickname;
    private String userIntroduce;
    private String userWebsite;
}
