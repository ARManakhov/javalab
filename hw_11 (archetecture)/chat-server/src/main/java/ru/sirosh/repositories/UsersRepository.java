package ru.sirosh.repositories;


import ru.sirosh.models.User;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<User> {
    Optional<User> findOneByUsername(String username);
    Optional<User> findOneById(Long id);
    }
