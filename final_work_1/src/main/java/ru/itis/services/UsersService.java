package ru.itis.services;

import ru.itis.models.User;

import java.util.List;
import java.util.Optional;

public interface UsersService {
    List<User> getUsers();

    Optional<User> getUser(Long id);

    void deleteUser(Long userId);
}
