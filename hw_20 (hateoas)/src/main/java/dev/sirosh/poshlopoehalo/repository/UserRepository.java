package dev.sirosh.poshlopoehalo.repository;

import dev.sirosh.poshlopoehalo.model.State;
import dev.sirosh.poshlopoehalo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAll();

    Optional<User> findUserByName(String name);

    Optional<User> findByMail(String email);

    Optional<User> findUserById(Long id);

}
