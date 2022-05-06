package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserProfileRes {
    private String userNickname;
    private String profileImgUrl;
    private String userCelleb;
    private Long boardCnt;
    private Long followerCnt;
    private Long followCnt;
    private String userName;
    private String userIntroduce;
    private String userWebsite;

}
