package ru.sirosh.Repositories;

import ru.sirosh.Models.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MessageRepositoryJdbcImpl implements MessageRepository {
    private Connection connection;
    private RowMapper<Message> messageRowMapper = rs ->
            new Message(
                    rs.getLong("id"),
                    rs.getString("text"),
                    rs.getTimestamp("time")
            );


    public MessageRepositoryJdbcImpl(Connection connection) {


        this.connection = connection;
    }

    @Override
    public List<Message> findMessagesInChat(int count) {
        String sqlQuery = "select * from (select * from \"message\" ORDER BY id DESC LIMIT ?" +
                ") ORDER BY id ASC";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            stmt.setLong(1, count);

            try (ResultSet rs = stmt.executeQuery()) {
                List<Message> m = new ArrayList<>();
                while (rs.next()) {
                    m.add(messageRowMapper.mapRow(rs));
                }
                return m;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

    }

    @Override
    public void save(Message message) {
        String sqlQuery = "insert into \"message\" (senderId,text,time) values (?, ?,?) returning id";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {

            stmt.setLong(1, message.senderId);
            stmt.setString(2, message.text);
            stmt.setTimestamp(3, message.timestamp);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                message.id = rs.getLong("id");
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void update(Message message) {

    }

    @Override
    public Optional<Message> find(Integer id) {
        return Optional.empty();
    }

    @Override
    public void delete(Message message) {

    }

    @Override
    public List<Message> findAll() {
        return null;
    }
}
