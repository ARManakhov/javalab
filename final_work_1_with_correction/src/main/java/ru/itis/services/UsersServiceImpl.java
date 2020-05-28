package ru.itis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.models.User;
import ru.itis.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getUsers(){

        return userRepository.findAll() ;
    }

    @Override
    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.delete((User) User.builder().id(userId).build());
    }
}
