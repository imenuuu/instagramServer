package com.example.demo.src.board.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Board {
    private Long positionInfo_id;
    private String boardDescription;
    private String boardImgUrl;
}
