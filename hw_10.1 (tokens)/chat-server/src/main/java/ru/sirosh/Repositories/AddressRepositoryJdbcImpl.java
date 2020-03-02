package ru.sirosh.Repositories;

import ru.sirosh.Models.Address;
import ru.sirosh.Models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AddressRepositoryJdbcImpl implements CrudRepository<Address> {
    private Connection connection;

    public AddressRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    RowMapper<Address> mapper = row -> new Address(
            row.getLong("id"),
            row.getString("description"),
            row.getLong("user_id")
    );

    @Override
    public void save(Address address) {
        String sqlQuery = "insert into \"address\" (description,user_id) values ( ?, ?) returning id";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {

            stmt.setString(1, address.description);
            stmt.setLong(2, address.userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                address.id = rs.getLong("id");
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void update(Address address) {

    }

    @Override
    public Optional<Address> find(Integer id) {
        String sqlQuery = "SELECT * FROM \"address\" WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                Address address = null;
                if (rs.next()) {
                    address = mapper.mapRow(rs);
                }
                return Optional.ofNullable(address);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }


    public List<Address> findByUserId(Long id) {
        String sqlQuery = "SELECT * FROM \"address\" WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Address> addresses = new ArrayList<>();
                while (rs.next()) {
                    addresses.add(mapper.mapRow(rs));
                }
                return addresses;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void delete(Address address) {
        String sqlQuery = "delete from \"address\" where id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            stmt.setLong(1, address.id);
            stmt.execute();
            address = null;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<Address> findAll() {
        return null;
    }
}
