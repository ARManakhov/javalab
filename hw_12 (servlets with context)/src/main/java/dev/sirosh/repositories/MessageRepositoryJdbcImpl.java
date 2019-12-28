package dev.sirosh.repositories;

import dev.sirosh.models.Message;
import dev.sirosh.models.MessageBuilder;

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
            MessageBuilder.aMessage()
            .withId(rs.getLong("id"))
            .withSenderId(rs.getLong("senderid"))
            .withText(rs.getString("text"))
            .withTimestamp(rs.getTimestamp("time"))
            .build();


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
    public List<Message> findLastMessageWithOffset(long num, long offset) {

        String sqlQuery = "select * from \"message\" order by id DESC limit ? offset ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            stmt.setLong(1, num);
            stmt.setLong(2, offset);

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

            stmt.setLong(1, message.getSenderId());
            stmt.setString(2, message.getText());
            stmt.setTimestamp(3, message.getTimestamp());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                message.setId(rs.getLong("id"));
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

    public long getCount() {
        String sqlQuery = "select count(*) from \"message\"";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                   return rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return 0;
    }
}
