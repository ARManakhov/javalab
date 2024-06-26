package ru.itis.repositories;

import org.springframework.stereotype.Repository;
import ru.itis.models.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByName(String email);

}
