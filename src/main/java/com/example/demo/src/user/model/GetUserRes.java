package com.example.demo.src.user.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
public class GetUserRes {
    private Long userIdx;
    private String userName;
    private String userPhonenumber;
    private String userEmail;
    private String userNickname;
    private String userPassword;
    private Date userBirth;

}
