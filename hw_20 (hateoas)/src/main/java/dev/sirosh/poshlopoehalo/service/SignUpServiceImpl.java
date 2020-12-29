package dev.sirosh.poshlopoehalo.service;

import dev.sirosh.poshlopoehalo.dto.DtoSignUp;
import dev.sirosh.poshlopoehalo.model.Role;
import dev.sirosh.poshlopoehalo.model.State;
import dev.sirosh.poshlopoehalo.model.User;
import dev.sirosh.poshlopoehalo.model.ValidationToken;
import dev.sirosh.poshlopoehalo.security.provider.JwtTokenProvider;
import dev.sirosh.poshlopoehalo.repository.UserRepository;
import dev.sirosh.poshlopoehalo.repository.ValidationTokenRepository;
import dev.sirosh.poshlopoehalo.security.details.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    ValidationTokenRepository validationTokenRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public void signUp(DtoSignUp form) {
        User user = User.builder()
                .mail(form.getMail())
                .hashPassword(passwordEncoder.encode(form.getPassword()))
                .name(form.getUsername())
                .state(State.NOT_CONFIRMED)
                .role(Role.USER)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);
        mailSignInSend(form.getMail(),new ValidationToken());
    }

    @Autowired
    MailService mailService;

    @Override
    public void mailSignInSend(String mail, ValidationToken token) {
        Optional<User> userOptional = userRepository.findByMail(mail);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String tokenString = jwtTokenProvider.createToken(user);
            token.setUser(user);
            token.setToken(tokenString);
            validationTokenRepository.save(token);
        }
    }

    @Override
    public boolean mailSignInValidate(String token) {
        if (jwtTokenProvider.validateToken(token)) {
            Optional<ValidationToken> tokenOptional = validationTokenRepository.findByToken(token);

            if (tokenOptional.isPresent()) {
                ValidationToken validationToken = tokenOptional.get();
                validationTokenRepository.delete(validationToken);
                User user = validationToken.getUser();
                if (user.getState()==State.NOT_CONFIRMED){
                    user.setState(State.CONFIRMED);
                    user = userRepository.save(user);
                }
                UserDetails userDetails = new UserDetailsImpl(user);
                Authentication auth = new  UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
                return true;
            }
        }
        return false;

    }


}
