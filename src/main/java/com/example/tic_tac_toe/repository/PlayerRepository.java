package com.example.tic_tac_toe.repository;

import com.example.tic_tac_toe.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}