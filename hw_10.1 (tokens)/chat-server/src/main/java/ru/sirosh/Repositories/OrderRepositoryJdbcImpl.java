package ru.sirosh.Repositories;

import ru.sirosh.Models.Address;
import ru.sirosh.Models.Order;
import ru.sirosh.Models.OrderProduct;
import ru.sirosh.Models.Product;

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

    RowMapper<Order> orderRowMapper = row -> new Order(
            row.getLong("id"),
            row.getLong("user_id"),
            row.getLong("address_id"),
            row.getString("status")

    );

    RowMapper<OrderProduct> orderProductRowMapper = row -> new OrderProduct(
            row.getLong("order_id"),
            row.getLong("product_id")
    );

    public List<Product> getProducts(Order order) {
        String sqlQuery = "select * from \"order_product\" where order_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            stmt.setLong(1, order.id);
            List<OrderProduct> cpList = new ArrayList<>();
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                cpList.add(orderProductRowMapper.mapRow(rs));
            }
            List<Product> productList = new ArrayList<>();
            for (OrderProduct orderProduct : cpList) {
                ProductRepositoryJdbcImpl prji = new ProductRepositoryJdbcImpl(connection);
                productList.add(prji.getById(orderProduct.productId));
            }
            return productList;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }


    public void addProduct(Order order, Product product) {
        String sqlQuery = "insert into \"order_product\" (order_id, product_id) values (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            OrderProduct orderProduct = new OrderProduct(order.id, product.id);
            stmt.setLong(1, orderProduct.orderId);
            stmt.setLong(2, orderProduct.productId);
            stmt.execute();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public void delProduct(Order order, Product product) {

        String sqlQuery = "delete from \"order_product\"where product_id = ? and order_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            OrderProduct orderProduct = new OrderProduct(order.id, product.id);
            stmt.setLong(1, orderProduct.productId);
            stmt.setLong(2, orderProduct.orderId);
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
                order.address = arji.find((int) order.addressId).get();
                order.productList = getProducts(order);
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
                    order.address = arji.find((int) order.addressId).get();
                    try {
                        order.productList = getProducts(order);
                    } catch (IllegalStateException e){
                        order.productList =  new ArrayList<>();
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
            stmt.setLong(1, order.userId);
            stmt.setLong(2, order.addressId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                order.id = rs.getLong("id");
            }
            for (Product product: order.productList) {
                addProduct(order,product);
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
