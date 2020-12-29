package dev.sirosh.poshlopoehalo.security.service;

import dev.sirosh.poshlopoehalo.model.User;
import dev.sirosh.poshlopoehalo.repository.UserRepository;
import dev.sirosh.poshlopoehalo.security.details.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service(value = "customUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findUserByName(email);
        if (userOptional.isPresent()) {
            User userSimple = userOptional.get();
            return new UserDetailsImpl(userSimple);
        } throw new UsernameNotFoundException("User not found");
    }

}
