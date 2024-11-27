package com.example.tic_tac_toe.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MoveRequest {
    private String player;
    private int row;
    private int col;
}
