package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class PostUserReq {
    private String userName;
    private String userPhonenumber;
    private String userEmail;
    private String userNickname;
    private String userPassword;
    private Date userBirth;
}
