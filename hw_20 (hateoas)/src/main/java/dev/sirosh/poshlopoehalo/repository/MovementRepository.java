package dev.sirosh.poshlopoehalo.repository;

import dev.sirosh.poshlopoehalo.model.City;
import dev.sirosh.poshlopoehalo.model.Movement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.util.List;


public interface MovementRepository extends JpaRepository<Movement, Long> {
    List<Movement> findAll();

    List<Movement> findAllByArrivalDateLessThanAndExpired(Timestamp timestamp, Boolean expired);

}
