package com.example.tic_tac_toe.service;

import com.example.tic_tac_toe.exception.PlayerNotFoundException;
import com.example.tic_tac_toe.model.Player;
import com.example.tic_tac_toe.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    // Crear un jugador
    public Player createPlayer(String name) {
        Player player = new Player();
        player.setName(name);
        return playerRepository.save(player);
    }

    // Obtener un jugador por ID
    public Player getPlayerById(Long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException("Player with ID " + id + " not found."));
    }

    // Listar todos los jugadores
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    // Actualizar un jugador
    public Player updatePlayer(Long id, String name) {
        Player player = getPlayerById(id);
        player.setName(name);
        return playerRepository.save(player);
    }

    // Eliminar un jugador
    public void deletePlayer(Long id) {
        if (!playerRepository.existsById(id)) {
            throw new PlayerNotFoundException("Player with ID " + id + " not found.");
        }
        playerRepository.deleteById(id);
    }
}