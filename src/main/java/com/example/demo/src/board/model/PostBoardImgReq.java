package com.example.demo.src.board.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostBoardImgReq {
    private Long user_id;
    private Long board_id;
    private String boardImgUrl;
}
