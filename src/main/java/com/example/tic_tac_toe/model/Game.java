package com.example.tic_tac_toe.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    private Board board = new Board(); // Inicializa el tablero

    private String currentPlayer;

    @Enumerated(EnumType.STRING)
    private GameStatus status;

    private String winner;

    // Método para reiniciar el juego
    public void resetGame() {
        board.initializeBoard();
        currentPlayer = null;
        status = GameStatus.ONGOING;
        winner = null;
    }
}
