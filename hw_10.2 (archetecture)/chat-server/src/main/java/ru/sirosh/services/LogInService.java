package ru.sirosh.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.sirosh.TokenizeUser;
import ru.sirosh.context.Component;
import ru.sirosh.dto.Dto;
import ru.sirosh.dto.DtoUser;
import ru.sirosh.dto.DtoUserBuilder;
import ru.sirosh.models.User;
import ru.sirosh.protocol.Request;
import ru.sirosh.repositories.UsersRepositoryJdbcImpl;

import java.sql.Connection;
import java.util.NoSuchElementException;

public class LogInService implements Component {
    private final String COMPONENT_NAME = "logInService";
    private PasswordEncoder encoder = new BCryptPasswordEncoder();
    Connection dbConnection;

    public LogInService(){}

    public void setDbConnection(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    @Override
    public Dto execute(Request req) {
        DtoUser dtoUser = (DtoUser) req.getData();
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


