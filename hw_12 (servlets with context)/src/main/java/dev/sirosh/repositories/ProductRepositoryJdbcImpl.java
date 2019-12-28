package dev.sirosh.repositories;

import dev.sirosh.models.Product;
import dev.sirosh.models.ProductBuilder;

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

    RowMapper<Product> mapper = row ->
            ProductBuilder.aProduct()
                    .withId(row.getLong("id"))
                    .withCost(row.getDouble("cost"))
                    .withName(row.getString("name"))
                    .withDescription(row.getString("description"))
                    .isDeleited(row.getBoolean("is_deleted"))
                    .build();

    @Override
    public void save(Product product) {
        String sqlQuery = "insert into \"product\" (name,description,cost) values ( ?, ?,?) returning id";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {

            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setDouble(3, product.getCost());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                product.setId(rs.getLong("id"));
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public List<Product> getList(long num, long offset) {
        String sqlQuery = "select * from \"product\" where is_deleted = false ORDER BY id DESC LIMIT ? offset ?";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            stmt.setLong(1, num);
            stmt.setLong(2, offset);

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
            stmt.setBoolean(1, product.isDeleted());
            stmt.setLong(2, product.getId());
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
