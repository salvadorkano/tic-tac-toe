package com.example.tic_tac_toe.controller;

import com.example.tic_tac_toe.model.Player;
import com.example.tic_tac_toe.service.PlayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    // Crear un nuevo jugador
    @PostMapping
    public ResponseEntity<Player> createPlayer(@RequestParam String name) {
        Player player = playerService.createPlayer(name);
        return ResponseEntity.ok(player);
    }

    // Obtener un jugador por ID
    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable Long id) {
        Player player = playerService.getPlayerById(id);
        return ResponseEntity.ok(player);
    }

    // Listar todos los jugadores
    @GetMapping
    public ResponseEntity<List<Player>> getAllPlayers() {
        List<Player> players = playerService.getAllPlayers();
        return ResponseEntity.ok(players);
    }

    // Actualizar un jugador
    @PutMapping("/{id}")
    public ResponseEntity<Player> updatePlayer(@PathVariable Long id, @RequestParam String name) {
        Player updatedPlayer = playerService.updatePlayer(id, name);
        return ResponseEntity.ok(updatedPlayer);
    }

    // Eliminar un jugador
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePlayer(@PathVariable Long id) {
        playerService.deletePlayer(id);
        return ResponseEntity.ok("Player " + id + " has been deleted.");
    }
}