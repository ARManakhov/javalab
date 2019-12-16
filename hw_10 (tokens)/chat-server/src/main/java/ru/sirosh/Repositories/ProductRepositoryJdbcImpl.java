package ru.sirosh.Repositories;

import ru.sirosh.Models.Message;
import ru.sirosh.Models.Product;
import ru.sirosh.Models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductRepositoryJdbcImpl implements CrudRepository<Product> {

    private Connection connection;

    public ProductRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    RowMapper<Product> mapper = row -> {
        return new Product(
                row.getLong("id"),
                row.getString("name"),
                row.getString("description"),
                row.getDouble("cost"),
                row.getBoolean("is_deleted")
        );
    };

    @Override
    public void save(Product product) {
        String sqlQuery = "insert into \"product\" (name,description,cost) values ( ?, ?,?) returning id";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {

            stmt.setString(1, product.name);
            stmt.setString(2, product.description);
            stmt.setDouble(3, product.cost);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                product.id = rs.getLong("id");
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public List<Product> getList(long num, long offset) {
        String sqlQuery = "select * from (select * from \"product\" where is_deleted = false ORDER BY id DESC LIMIT ? ) AS list ORDER BY id ASC LIMIT ?";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            stmt.setLong(1, offset + num);
            stmt.setLong(2, num);

            try (ResultSet rs = stmt.executeQuery()) {
                List<Product> products = new ArrayList<>();
                while (rs.next()) {
                    products.add(mapper.mapRow(rs));
                }
                return products;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public Product getById(long id) {
        String sqlQuery = "select * from \"product\" where id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                Product product = null;
                if (rs.next()) {
                    product = mapper.mapRow(rs);
                }

                return product;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void update(Product product) {

    }

    @Override
    public Optional<Product> find(Integer id) {

        return Optional.empty();
    }

    @Override
    public void delete(Product product) {
        String sqlQuery = "update \"product\" set is_deleted = ? where id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            stmt.setBoolean(1, product.isDeleted);
            stmt.setLong(2, product.id);
            stmt.execute();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<Product> findAll() {
        return null;
    }

    public long getCount() {
        String sqlQuery = "select count(*) from \"product\"";
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
