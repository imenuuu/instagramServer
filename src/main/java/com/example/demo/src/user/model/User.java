package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private Long userIdx;
    private String profileImgUrl;
    private String userPassword;
    private String userWebsite;
    private String userNickname;
    private String userName;
    private String userPhonenumber;
    private String userEmail;
    private String userIntroduce;
    private String userReactNow;
    private String userSex;
    private String userPublic;
    private String userCelleb;
    private Timestamp userUpdated_date;
    private Timestamp userActived_date;
    private Timestamp userStatus;
    private Timestamp userCreated_date;
    private Date userBirth;
}
