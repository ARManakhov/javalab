package dev.sirosh.poshlopoehalo.service;

import dev.sirosh.poshlopoehalo.model.City;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CityService {
    Optional<City> get(Long id);

    City get(String name);

    List<City> getAll();

    boolean add(City City);
}