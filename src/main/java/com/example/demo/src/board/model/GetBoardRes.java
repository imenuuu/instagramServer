package com.example.demo.src.board.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class GetBoardRes {
    private Long boardIdx;
    private String profileImgUrl;
    private String userNickname;
    private String boardImgUrl;
    private String likeCnt;
    private String boarduserNickname;
    private String boardDescription;
    private String chatCnt;
    private String boardTime;

}
