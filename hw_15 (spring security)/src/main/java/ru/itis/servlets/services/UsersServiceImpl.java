package ru.itis.servlets.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.servlets.dto.DtoUser;
import ru.itis.servlets.repositories.UserRepository;

import java.util.List;

@Component
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<DtoUser> getAllUsers() {
        return DtoUser.from(userRepository.findAll());
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.delete(userId);
    }
}
