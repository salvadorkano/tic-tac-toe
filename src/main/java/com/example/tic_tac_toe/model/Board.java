package com.example.tic_tac_toe.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Board {

    private static final int SIZE = 3; // Tamaño del tablero (3x3)
    private String[][] board;

    public Board() {
        this.board = new String[SIZE][SIZE]; // Inicializa el tablero vacío
        initializeBoard();
    }

    // Inicializa el tablero con espacios vacíos
    public void initializeBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = ""; // Tablero vacío
            }
        }
    }

    // Método para hacer un movimiento en el tablero
    public boolean makeMove(int row, int col, String player) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE || !board[row][col].isEmpty()) {
            return false; // Movimiento inválido
        }
        board[row][col] = player; // Marca el movimiento
        return true;
    }

    // Verifica si hay un ganador
    public String checkWinner() {
        // Verifica filas y columnas
        for (int i = 0; i < SIZE; i++) {
            if (!board[i][0].isEmpty() && board[i][0].equals(board[i][1]) && board[i][0].equals(board[i][2])) {
                return board[i][0]; // Gana en una fila
            }
            if (!board[0][i].isEmpty() && board[0][i].equals(board[1][i]) && board[0][i].equals(board[2][i])) {
                return board[0][i]; // Gana en una columna
            }
        }
        // Verifica diagonales
        if (!board[0][0].isEmpty() && board[0][0].equals(board[1][1]) && board[0][0].equals(board[2][2])) {
            return board[0][0]; // Gana en diagonal principal
        }
        if (!board[0][2].isEmpty() && board[0][2].equals(board[1][1]) && board[0][2].equals(board[2][0])) {
            return board[0][2]; // Gana en diagonal inversa
        }

        return null; // No hay ganador
    }

    // Verifica si el tablero está lleno
    public boolean isFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j].isEmpty()) {
                    return false; // Hay espacios vacíos
                }
            }
        }
        return true; // Tablero lleno
    }

    // Método para imprimir el tablero (opcional para depuración)
    public void printBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j].isEmpty() ? "-" : board[i][j]);
                if (j < SIZE - 1) System.out.print(" | ");
            }
            System.out.println();
            if (i < SIZE - 1) System.out.println("---------");
        }
    }
}
