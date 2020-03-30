package ru.itis.servlets.services;

import ru.itis.servlets.dto.DtoUser;

import java.util.List;

public interface UsersService {
    List<DtoUser> getAllUsers();

    void deleteUser(Long userId);
}
