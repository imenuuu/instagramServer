package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
public class PostOauthUserReq {
    private String userPhonenumber;
    private String userNickname;
    private Date userBirth;
}
