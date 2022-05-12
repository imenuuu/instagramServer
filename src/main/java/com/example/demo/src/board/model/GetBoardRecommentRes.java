package com.example.demo.src.board.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetBoardRecommentRes {
    private String profileImgUrl;
    private String userNickname;
    private String recomment;
    private String reCommentDate;
}
