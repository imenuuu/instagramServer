package com.example.demo.src.board.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReComment {
    private Long comment_id;
    private Long board_id;
    private Long user_id;
    private String recomment;
}
