package ru.itis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.dto.DtoUser;
import ru.itis.models.User;
import ru.itis.repositories.UserRepository;

import java.util.List;

@Component
@Transactional
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public void deleteUser(Long userId) {
        userRepository.delete(User.builder().id(userId).build());
    }
}
