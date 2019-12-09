package ru.sirosh.Repositories;


import ru.sirosh.Models.User;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<User> {
    Optional<User> findOneByUsername(String username);
}
