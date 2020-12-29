package dev.sirosh.poshlopoehalo.controller.api;

import dev.sirosh.poshlopoehalo.dto.DtoSignIn;
import dev.sirosh.poshlopoehalo.dto.DtoSignUp;
import dev.sirosh.poshlopoehalo.security.provider.JwtTokenProvider;
import dev.sirosh.poshlopoehalo.repository.UserRepository;
import dev.sirosh.poshlopoehalo.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        Map<Object, Object> model = new HashMap<>();

        try {

            String username = data.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
            String token = jwtTokenProvider.createToken(userRepository.findUserByName(username).orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found")));
            model.put("username", username);
            model.put("token", token);
            return ok(model);
        } catch (
                AuthenticationException e) {

            model.put("status", "err");
            return ok(model);
        }
    }

    @Autowired
    SignUpService signUpService;


    @PostMapping("/api/sign_up")
    public ResponseEntity signUpMethod( DtoSignUp dtoSignUp) {
        Map<Object, Object> model = new HashMap<>();

        if (dtoSignUp.getPassword().equals(dtoSignUp.getPasswordCheck())) {
            signUpService.signUp(dtoSignUp);
            String username = dtoSignUp.getUsername();
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
