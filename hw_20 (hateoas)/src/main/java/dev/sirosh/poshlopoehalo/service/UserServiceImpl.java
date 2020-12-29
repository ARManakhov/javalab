package dev.sirosh.poshlopoehalo.service;

import dev.sirosh.poshlopoehalo.dto.DtoSignUp;
import dev.sirosh.poshlopoehalo.model.Role;
import dev.sirosh.poshlopoehalo.model.State;
import dev.sirosh.poshlopoehalo.model.User;
import dev.sirosh.poshlopoehalo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    public Optional<User> getUser(Long id) {
        return userRepository.findUserById(id);
    }


    public void signUp(DtoSignUp form) {
        User user = User.builder()
                .mail(form.getMail())
                .name(form.getUsername())
                .state(State.NOT_CONFIRMED)
                .role(Role.USER)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);
    }
}
