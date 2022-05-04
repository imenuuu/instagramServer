package com.example.demo.src.board.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetBoardCommentRes {
    private String commentprofileImgUrl;
    private String commentuserNickName;
    private String comment;
    private String commentDate;
    private String recommentprofileImgUrl;
    private String recommentuserNickName;
    private String reComment;
    private String reCommentDate;
}
