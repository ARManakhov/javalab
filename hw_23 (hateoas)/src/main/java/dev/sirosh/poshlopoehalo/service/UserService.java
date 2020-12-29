package dev.sirosh.poshlopoehalo.service;

import dev.sirosh.poshlopoehalo.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public interface UserService {
    Optional<User> getUser(Long id);
}
