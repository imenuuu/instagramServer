package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostUserKakaoReq {
    private String k_name;
    private Long k_id;
    private String k_email;

}
