package ru.sirosh.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.sirosh.TokenizeUser;
import ru.sirosh.context.Component;
import ru.sirosh.dto.Dto;
import ru.sirosh.dto.DtoUser;
import ru.sirosh.models.User;
import ru.sirosh.models.UserBuilder;
import ru.sirosh.models.request.ReqWithUser;
import ru.sirosh.protocol.Request;
import ru.sirosh.repositories.UsersRepositoryJdbcImpl;

import java.sql.Connection;
import java.util.NoSuchElementException;

public class RegSevice implements Component {
    private final String COMPONENT_NAME = "regService";
    private PasswordEncoder encoder = new BCryptPasswordEncoder();
    Connection dbConnection;

    public RegSevice(){}

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
        dtoUser.setPassword(encoder.encode(dtoUser.getPassword()));
        try {
            UsersRepositoryJdbcImpl urji = new UsersRepositoryJdbcImpl(dbConnection);
            User dbUser = urji.findOneByUsername(dtoUser.getUsername()).get();
            return null;
        } catch (NoSuchElementException e) {
            User dbUser = UserBuilder.anUser()
                    .withUsername(dtoUser.getUsername())
                    .withPassword(dtoUser.getPassword())
                    .build();
            new UsersRepositoryJdbcImpl(dbConnection).save(dbUser);
            dtoUser.setId(dbUser.getId());
            dtoUser.setRole(dbUser.getRole());
            dtoUser.setPassword(null);
            dtoUser.setToken(new TokenizeUser().createJWT(dbUser));
            return dtoUser;
        }
    }
}
