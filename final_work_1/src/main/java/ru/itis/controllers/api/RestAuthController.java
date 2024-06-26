package ru.itis.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.dto.DtoSignIn;
import ru.itis.dto.DtoSignUp;
import ru.itis.provider.JwtTokenProvider;
import ru.itis.repositories.UserRepository;
import ru.itis.services.SignUpService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class RestAuthController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/api/sign_in")
    public ResponseEntity signin(DtoSignIn data) {
        try {

            String username = data.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
            String token = jwtTokenProvider.createToken( userRepository.findUserByName(username).orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found")));
            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);
            return ok(model);
        } catch (
                AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }


    @Autowired
    SignUpService signUpService;


    @PostMapping("/api/sign_up")
    public ResponseEntity signUpMethod(@Valid DtoSignUp dtoSignUp) {
        Map<Object, Object> model = new HashMap<>();

        if (dtoSignUp.getPassword().equals(dtoSignUp.getPasswordCheck())) {
            signUpService.signUp(dtoSignUp);
            String username = dtoSignUp.getName();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, dtoSignUp.getPassword()));
            String token = jwtTokenProvider.createToken(userRepository.findUserByName(username).orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found")));
            model.put("username", username);
            model.put("token", token);
            return ok(model);

        } else {
            model.put("status", "err");
            return ok(model);
        }
    }

}
