package dev.sirosh.poshlopoehalo.repository;

import dev.sirosh.poshlopoehalo.model.City;
import dev.sirosh.poshlopoehalo.model.Transport;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransportRepository extends CrudRepository<Transport,Long> {
    List<Transport> findAll();
}
