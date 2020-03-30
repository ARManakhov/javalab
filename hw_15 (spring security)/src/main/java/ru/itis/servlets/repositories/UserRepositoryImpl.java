package ru.itis.servlets.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.itis.servlets.models.Role;
import ru.itis.servlets.models.State;
import ru.itis.servlets.models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Component
public class UserRepositoryImpl implements UserRepository {

    private final String SAVE_USER = "insert into users (name,password,email,role,state) values (?,?,?,?,?)";
    private final String FIND_USER_ID = "select * from users where id = ?";
    private final String FIND_USER_EMAIL = "select * from users where email = ?";
    private final String FIND_ALL_USER = "select * from users";

    RowMapper<User> userRowMapper = (ResultSet rs, int id) -> User.builder()
            .id(rs.getLong("id"))
            .name(rs.getString("name"))
            .hashPassword(rs.getString("password"))
            .email(rs.getString("email"))
            .role(Role.valueOf(rs.getString("role")))
            .state(State.valueOf(rs.getString("state")))
            .build();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<User> findUserById(Long aLong) {
        jdbcTemplate.query(connection -> {
            PreparedStatement statement = connection.prepareStatement(FIND_USER_ID);
            statement.setLong(1, aLong);
            return statement;
        }, userRowMapper);
        return Optional.empty();
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        List<User> user = jdbcTemplate.query(connection -> {
            PreparedStatement statement = connection.prepareStatement(FIND_USER_EMAIL);
            statement.setString(1, email);
            return statement;
        }, userRowMapper);
        if (user.size() == 0 ){
            return Optional.empty();
        }else {
            return Optional.of(user.get(0));

        }
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(FIND_ALL_USER, userRowMapper);
    }

    @Override
    public void save(User entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SAVE_USER, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getHashPassword());
            statement.setString(3, entity.getEmail());
            statement.setString(4, entity.getRole().toString());
            statement.setString(5, entity.getState().toString());
            return statement;
        }, keyHolder);
        entity.setId((Long) keyHolder.getKeys().get("id"));
    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public void update(User entity) {

    }
}
