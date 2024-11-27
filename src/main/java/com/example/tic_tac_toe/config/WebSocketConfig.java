package com.example.tic_tac_toe.config;

import com.example.tic_tac_toe.TutorialHandler;
import com.example.tic_tac_toe.websocket.GameHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic"); // Habilita un broker simple para enviar mensajes a los clientes
        registry.setApplicationDestinationPrefixes("/app"); // Prefijo para mensajes enviados desde el cliente
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") // Endpoint WebSocket
                .setAllowedOrigins("*")
                .withSockJS(); // Soporte para SockJS
    }
}