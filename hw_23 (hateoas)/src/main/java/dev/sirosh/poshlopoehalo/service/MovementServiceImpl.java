package dev.sirosh.poshlopoehalo.service;

import dev.sirosh.poshlopoehalo.model.Movement;
import dev.sirosh.poshlopoehalo.repository.MovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovementServiceImpl implements MovementService {
    @Autowired
    MovementRepository movementRepository;

    @Override
    public Optional<Movement> get(Long id) {
        return movementRepository.findById(id);
    }

    @Override
    public List<Movement> getAll() {
        return movementRepository.findAll();
    }

    @Override
    public boolean add(Movement movement) {
        try {
            movementRepository.save(movement);
            return true;
        } catch (Exception e) {
            return false;
        }
    }



}
