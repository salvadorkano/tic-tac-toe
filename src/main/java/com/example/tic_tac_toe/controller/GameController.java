package com.example.tic_tac_toe.controller;

import com.example.tic_tac_toe.model.Game;
import com.example.tic_tac_toe.model.MoveRequest;
import com.example.tic_tac_toe.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    // Crear una nueva partida
    @PostMapping("/create")
    public ResponseEntity<Game> createGame(@RequestParam String player1, @RequestParam String player2) {
        Game game = gameService.createGame(player1, player2);
        return ResponseEntity.ok(game);
    }

    // Realizar un movimiento
    @PostMapping("/{gameId}/move")
    public ResponseEntity<Game> makeMove(@PathVariable Long gameId, @RequestBody MoveRequest moveRequest) {
        Game updatedGame = gameService.makeMove(gameId, moveRequest);
        return ResponseEntity.ok(updatedGame);
    }

    // Obtener el estado de un juego
    @GetMapping("/{gameId}")
    public ResponseEntity<Game> getGameState(@PathVariable Long gameId) {
        Game game = gameService.getGameState(gameId);
        return ResponseEntity.ok(game);
    }

    // Listar todas las partidas activas
    @GetMapping
    public ResponseEntity<List<Game>> getAllGames() {
        List<Game> games = gameService.getAllGames();
        return ResponseEntity.ok(games);
    }

    // Terminar una partida
    @DeleteMapping("/{gameId}")
    public ResponseEntity<String> terminateGame(@PathVariable Long gameId) {
        gameService.terminateGame(gameId);
        return ResponseEntity.ok("Game " + gameId + " has been terminated.");
    }
}
