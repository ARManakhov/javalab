package ru.sirosh.Repositories;

import ru.sirosh.Models.Cart;
import ru.sirosh.Models.CartProduct;
import ru.sirosh.Models.Product;
import ru.sirosh.Models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CartRepositoryJdbcImpl implements CrudRepository<Cart> {

    private Connection connection;

    public CartRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    RowMapper<Cart> cartRowMapper = row -> new Cart(
            row.getLong("id"),
            row.getLong("user_id")
    );

    RowMapper<CartProduct> cartProductRowMapper = row -> new CartProduct(
            row.getLong("cart_id"),
            row.getLong("product_id")
    );

    public List<Product> getProducts(Cart cart) {
        String sqlQuery = "select  * from \"cart_product\" where cart_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            stmt.setLong(1, cart.id);
            List<CartProduct> cpList = new ArrayList<>();
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                cpList.add(cartProductRowMapper.mapRow(rs));
            }
            List<Product> productList = new ArrayList<>();
            for (CartProduct cartProduct : cpList) {
                ProductRepositoryJdbcImpl prji = new ProductRepositoryJdbcImpl(connection);
                productList.add(prji.getById(cartProduct.productId));
            }
            return productList;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }


    public void addProduct(Cart cart, Product product) {
        String sqlQuery = "insert into \"cart_product\" (cart_id, product_id) values (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            CartProduct cartProduct = new CartProduct(cart.id, product.id);
            stmt.setLong(1, cartProduct.cartId);
            stmt.setLong(2, cartProduct.productId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public void delProduct(Cart cart, Product product) {

        String sqlQuery = "delete from \"cart_product\"where product_id = ? and cart_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            CartProduct cartProduct = new CartProduct(cart.id, product.id);
            stmt.setLong(1, cartProduct.productId);
            stmt.setLong(2, cartProduct.cartId);
            stmt.execute();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public void delAllProducts(Cart cart) {

        String sqlQuery = "delete from \"cart_product\"where cart_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            stmt.setLong(1, cart.id);
            stmt.execute();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public Cart getCartByUserId(long id) {
        String sqlQuery = "SELECT * FROM \"cart\" WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                Cart cart = null;

                if (rs.next()) {
                    cart = cartRowMapper.mapRow(rs);
                }
                if (cart == null){
                    cart = new Cart(id);
                    save(cart);
                }
                cart.productList = getProducts(cart);
                return cart;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void save(Cart cart) {
        String sqlQuery = "insert into \"cart\" (user_id) values ( ?) returning id";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery)) {
            stmt.setLong(1, cart.userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                cart.id = rs.getLong("id");
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }


    @Override
    public void update(Cart cart) {

    }

    @Override
    public Optional<Cart> find(Integer id) {
        return Optional.empty();
    }

    @Override
    public void delete(Cart cart) {

    }

    @Override
    public List<Cart> findAll() {
        return null;
    }
}
