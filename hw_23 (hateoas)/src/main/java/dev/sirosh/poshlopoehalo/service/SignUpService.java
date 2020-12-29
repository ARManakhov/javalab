package dev.sirosh.poshlopoehalo.service;


import dev.sirosh.poshlopoehalo.dto.DtoSignUp;
import dev.sirosh.poshlopoehalo.model.User;
import dev.sirosh.poshlopoehalo.model.ValidationToken;
import org.springframework.stereotype.Service;

@Service
public interface SignUpService {
    void signUp(DtoSignUp form);


    void mailSignInSend(String mail, ValidationToken validationToken);

    boolean mailSignInValidate(String token);
}
