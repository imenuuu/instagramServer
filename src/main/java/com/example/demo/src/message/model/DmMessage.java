package com.example.demo.src.message.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DmMessage {
    private Long dmmessageIdx;
    private Long dmRoom_id;
    private Long user_id;
    private String dmMessage;
    private String createMessage;
}
