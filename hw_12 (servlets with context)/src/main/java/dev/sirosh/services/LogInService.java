package dev.sirosh.services;

import dev.sirosh.TokenizeUser;
import dev.sirosh.dto.DtoUser;
import dev.sirosh.models.User;
import dev.sirosh.repositories.UsersRepositoryJdbcImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import dev.sirosh.dto.Dto;
import dev.sirosh.dto.DtoUserBuilder;

import java.sql.Connection;
import java.util.NoSuchElementException;

public class LogInService {
    private final String COMPONENT_NAME = "logInService";
    private PasswordEncoder encoder = new BCryptPasswordEncoder();
    Connection dbConnection;


    public LogInService(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public Dto execute(DtoUser dtoUser) {
        try {
            UsersRepositoryJdbcImpl urji = new UsersRepositoryJdbcImpl(dbConnection);
            User dbUser =  urji.findOneByUsername(dtoUser.getUsername()).get();
            if (encoder.matches(dtoUser.getPassword(), dbUser.getPassword())) {
                return DtoUserBuilder.aDtoUser()
                        .withId(dbUser.getId())
                        .withUsername(dbUser.getUsername())
                        .withRole(dbUser.getRole())
                        .withToken(new TokenizeUser().createJWT(dbUser))
                        .build();
            } else {
                return null;
            }
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}


