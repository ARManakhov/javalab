package ru.itis.servlets.repositories;

import ru.itis.servlets.models.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<Long, User> {
    Optional<User> findUserById(Long aLong);
    Optional<User> findUserByEmail(String email);
}
