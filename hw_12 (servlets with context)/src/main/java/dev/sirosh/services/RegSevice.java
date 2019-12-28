package dev.sirosh.services;

import dev.sirosh.TokenizeUser;
import dev.sirosh.dto.Dto;
import dev.sirosh.dto.DtoUser;
import dev.sirosh.models.User;
import dev.sirosh.models.UserBuilder;
import dev.sirosh.repositories.UsersRepositoryJdbcImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Connection;
import java.util.NoSuchElementException;

public class RegSevice  {
    private PasswordEncoder encoder = new BCryptPasswordEncoder();
    Connection dbConnection;


    public RegSevice(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public Dto execute(DtoUser dtoUser) {
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
