package dev.sirosh.poshlopoehalo.repository;

import dev.sirosh.poshlopoehalo.model.ValidationToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ValidationTokenRepository extends CrudRepository<ValidationToken,Long> {
    Optional<ValidationToken> findByToken(String token);
}
