package dev.sirosh.poshlopoehalo.repository;

import dev.sirosh.poshlopoehalo.model.Movement;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovementRepository extends MongoRepository<Movement, String> {
    @Query(value = "{ price: $lt: ?price}")
    List<Movement> findLtPrice(Double price);
}
