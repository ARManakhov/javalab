package dev.sirosh.poshlopoehalo.service;

import dev.sirosh.poshlopoehalo.model.Movement;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface MovementService {
    Optional<Movement> get(Long id);
    List<Movement> getAll();
    boolean add(Movement movement);
}