package com.example.demo.src.user.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostUserKakaoRes {
    private Long userIdx;
    private String jwt;
    private String refreshToken;
}
