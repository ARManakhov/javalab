package dev.sirosh.poshlopoehalo.service;

import dev.sirosh.poshlopoehalo.model.City;
import dev.sirosh.poshlopoehalo.model.Transport;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface TransportService {
    Optional<Transport> get(Long id);

    List<Transport> getAll();

    boolean add(Transport transport);
}