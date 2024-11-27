package com.example.tic_tac_toe.service;

import com.example.tic_tac_toe.exception.GameNotFoundException;
import com.example.tic_tac_toe.exception.InvalidMoveException;
import com.example.tic_tac_toe.model.Board;
import com.example.tic_tac_toe.model.Game;
import com.example.tic_tac_toe.model.GameStatus;
import com.example.tic_tac_toe.model.MoveRequest;
import com.example.tic_tac_toe.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final GameWebSocketService gameWebSocketService;

    public GameService(GameRepository gameRepository, GameWebSocketService gameWebSocketService) {
        this.gameRepository = gameRepository;
        this.gameWebSocketService = gameWebSocketService;
    }

    // Crear una nueva partida
    public Game createGame(String player1, String player2) {
        Game game = new Game();
        game.setBoard(new Board()); // Usa el modelo Board
        game.setCurrentPlayer(player1);
        game.setStatus(GameStatus.ONGOING);
        gameRepository.save(game);

        // Notificar vía WebSocket (si aplica)
        gameWebSocketService.sendGameUpdate(game.getId().toString(), game);

        return game;
    }

    // Realizar un movimiento
    public Game makeMove(Long gameId, MoveRequest move) {
        Game game = getGameById(gameId);

        if (!game.getStatus().equals(GameStatus.ONGOING)) {
            throw new InvalidMoveException("Game is not ongoing.");
        }

        if (!game.getCurrentPlayer().equals(move.getPlayer())) {
            throw new InvalidMoveException("It's not " + move.getPlayer() + "'s turn.");
        }

        // Realizar el movimiento utilizando el modelo Board
        boolean moveSuccessful = game.getBoard().makeMove(move.getRow(), move.getCol(), move.getPlayer().substring(0, 1));
        if (!moveSuccessful) {
            throw new InvalidMoveException("Invalid move. Cell is already occupied or out of bounds.");
        }

        // Verificar ganador o empate
        String winner = game.getBoard().checkWinner();
        if (winner != null) {
            game.setStatus(GameStatus.COMPLETED);
            game.setWinner(move.getPlayer());
        } else if (game.getBoard().isFull()) {
            game.setStatus(GameStatus.TIE);
        } else {
            // Cambiar turno
            String nextPlayer = game.getCurrentPlayer().equals(move.getPlayer()) ? "Player 2" : "Player 1";
            game.setCurrentPlayer(nextPlayer);
        }

        // Notificar actualización del juego vía WebSocket (si aplica)
        gameWebSocketService.sendGameUpdate(gameId.toString(), game);

        return gameRepository.save(game);
    }

    // Obtener el estado de un juego
    public Game getGameState(Long gameId) {
        return getGameById(gameId);
    }

    // Listar todas las partidas activas
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    // Terminar una partida
    public void terminateGame(Long gameId) {
        Game game = getGameById(gameId);
        game.setStatus(GameStatus.COMPLETED);
        gameRepository.save(game);
    }

    // Métodos auxiliares
    private Game getGameById(Long gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException("Game with ID " + gameId + " not found."));
    }
}
