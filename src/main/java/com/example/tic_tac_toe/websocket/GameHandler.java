package com.example.tic_tac_toe.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;
@Slf4j
public class GameHandler extends TextWebSocketHandler {

    // Mapa para almacenar los estados del juego por sesión.
    private final Map<String, String[][]> games = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("Connection established on session: {}", session.getId());
        // Inicializa un tablero vacío para cada sesión.
        games.put(session.getId(), new String[3][3]);
        session.sendMessage(new TextMessage("Welcome! Let's play Tic-Tac-Toe."));
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("Received message: {}", payload);

        // Procesa el mensaje (ejemplo: "player1,1,2").
        String[] parts = payload.split(",");
        if (parts.length != 3) {
            session.sendMessage(new TextMessage("Invalid format. Use: player,row,column"));
            return;
        }

        String player = parts[0];
        int row = Integer.parseInt(parts[1]);
        int column = Integer.parseInt(parts[2]);

        // Actualiza el estado del tablero.
        String[][] board = games.get(session.getId());
        if (board[row][column] == null) {
            board[row][column] = player;
            session.sendMessage(new TextMessage("Move accepted: " + player + " at [" + row + "," + column + "]"));
        } else {
            session.sendMessage(new TextMessage("Invalid move! Cell already taken."));
        }

        // Envía el estado actual del tablero.
        String boardState = printBoard(board);
        session.sendMessage(new TextMessage("Current board:\n" + boardState));
    }

    private String printBoard(String[][] board) {
        StringBuilder builder = new StringBuilder();
        for (String[] row : board) {
            for (String cell : row) {
                builder.append(cell == null ? "-" : cell).append(" ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("Connection closed on session: {} with status: {}", session.getId(), status);
        games.remove(session.getId()); // Limpia el estado del juego.
    }
}