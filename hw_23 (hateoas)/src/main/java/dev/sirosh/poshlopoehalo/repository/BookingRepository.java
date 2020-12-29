package dev.sirosh.poshlopoehalo.repository;

import dev.sirosh.poshlopoehalo.model.Booking;
import dev.sirosh.poshlopoehalo.model.City;
import dev.sirosh.poshlopoehalo.model.Movement;
import dev.sirosh.poshlopoehalo.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends CrudRepository<Booking,Long> {
    List<Booking> findAll();
    Optional<Booking> findByUserAndMovement(User user, Movement movement);
}
