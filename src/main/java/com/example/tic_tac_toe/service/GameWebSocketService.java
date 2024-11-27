package com.example.tic_tac_toe.service;

import com.example.tic_tac_toe.model.Game;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class GameWebSocketService {

   private final SimpMessagingTemplate messagingTemplate;

    public GameWebSocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // Enviar una actualización del juego
    public void sendGameUpdate(String gameId, Game game) {
        messagingTemplate.convertAndSend("/topic/games/" + gameId, game);
    }
}