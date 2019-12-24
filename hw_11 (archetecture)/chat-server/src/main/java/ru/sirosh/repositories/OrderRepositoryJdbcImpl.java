package ru.sirosh.repositories;

import ru.sirosh.models.Order;
import ru.sirosh.models.OrderBuilder;
import ru.sirosh.models.OrderProduct;
import ru.sirosh.models.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderRepositoryJdbcImpl implements CrudRepository<Order> {

    private Connection connection;

    public OrderRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    RowMapper<Order> orderRowMapper = row ->
            OrderBuilder.anOrder()
                    .withId(row.getLong("id"))
                    .withUserId(row.getLong("user_id"))
                    .withAddressId(row.getLong("address_id"))
                    .withStatus(row.getString("status"))
                    .build();

    RowMapper<OrderProduct> orderProductRowMapper = row -> new OrderProduct(
            row.getLong("order_id"),
            row.getLong("product_id")
    );

    public List<Product> getProducts(Order order) {
        String sqlQuery = "select * from \"order_product\" where order_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            stmt.setLong(1, order.getId());
            List<OrderProduct> cpList = new ArrayList<>();
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                cpList.add(orderProductRowMapper.mapRow(rs));
            }
            List<Product> productList = new ArrayList<>();
            for (OrderProduct orderProduct : cpList) {
                ProductRepositoryJdbcImpl prji = new ProductRepositoryJdbcImpl(connection);
                productList.add(prji.getById(orderProduct.getProductId()));
            }
            return productList;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }


    public void addProduct(Order order, Product product) {
        String sqlQuery = "insert into \"order_product\" (order_id, product_id) values (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            OrderProduct orderProduct = new OrderProduct(order.getId(), product.getId());
            stmt.setLong(1, orderProduct.getOrderId());
            stmt.setLong(2, orderProduct.getProductId());
            stmt.execute();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public void delProduct(Order order, Product product) {

        String sqlQuery = "delete from \"order_product\"where product_id = ? and order_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            OrderProduct orderProduct = new OrderProduct(order.getId(), product.getId());
            stmt.setLong(1, orderProduct.getProductId());
            stmt.setLong(2, orderProduct.getOrderId());
            stmt.execute();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public Order getOrderById(long id) {
        String sqlQuery = "SELECT * FROM \"order\" WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                Order order = null;

                if (rs.next()) {
                    order = orderRowMapper.mapRow(rs);
                }
                AddressRepositoryJdbcImpl arji = new AddressRepositoryJdbcImpl(connection);
                order.setAddress(arji.find((int) order.getAddressId()).get());
                order.setProductList(getProducts(order));
                return order;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public List<Order> getOrderByUserId(long id) {
        String sqlQuery = "SELECT * FROM \"order\" WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Order> orders = new ArrayList<>();

                while (rs.next()) {
                    Order order = (orderRowMapper.mapRow(rs));
                    AddressRepositoryJdbcImpl arji = new AddressRepositoryJdbcImpl(connection);
                    order.setAddress(arji.find((int) order.getAddressId()).get());
                    try {
                        order.setProductList(getProducts(order));
                    } catch (IllegalStateException e) {
                        order.setProductList(new ArrayList<>());
                    }
                    orders.add(order);
                }

                return orders;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void save(Order order) {
        String sqlQuery = "insert into \"order\" (user_id,address_id,status) values (?,?,'processing') returning id";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            stmt.setLong(1, order.getUserId());
            stmt.setLong(2, order.getAddressId());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                order.setId(rs.getLong("id"));
            }
            for (Product product : order.getProductList()) {
                addProduct(order, product);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }


    @Override
    public void update(Order order) {

    }

    @Override
    public Optional<Order> find(Integer id) {
        return Optional.empty();
    }

    @Override
    public void delete(Order order) {

    }

    @Override
    public List<Order> findAll() {
        return null;
    }
}
