package dev.sirosh.poshlopoehalo.reposetory;

import dev.sirosh.poshlopoehalo.model.City;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends MongoRepository<City, String> {

}
