package dev.sirosh.poshlopoehalo.repository;

import dev.sirosh.poshlopoehalo.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends JpaRepository<City,Long> {
    List<City> findAll();
    City findByName(String name);


}
