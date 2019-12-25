package ru.sirosh.services;

import ru.sirosh.TokenizeUser;
import ru.sirosh.context.Component;
import ru.sirosh.dto.*;
import ru.sirosh.models.Cart;
import ru.sirosh.models.Product;
import ru.sirosh.models.User;
import ru.sirosh.network.SocketsManager;
import ru.sirosh.protocol.Request;
import ru.sirosh.repositories.CartRepositoryJdbcImpl;
import ru.sirosh.repositories.ProductRepositoryJdbcImpl;
import ru.sirosh.repositories.UsersRepository;
import ru.sirosh.repositories.UsersRepositoryJdbcImpl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class GetCartService implements Component {

    SocketsManager socketsManager;
    Connection dbConnection;


    public void setDbConnection(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void setSocketsManager(SocketsManager socketsManager) {
        this.socketsManager = socketsManager;
    }

    @Override
    public String getComponentName() {
        return "get_cart";
    }

    @Override
    public Dto execute(Request req) {
        ProductRepositoryJdbcImpl prji = new ProductRepositoryJdbcImpl(dbConnection);
        UsersRepository urji = new UsersRepositoryJdbcImpl(dbConnection);
        List<DtoProduct> products = new ArrayList<>();
        CartRepositoryJdbcImpl crji = new CartRepositoryJdbcImpl(dbConnection);
        User reqUser = new TokenizeUser().decodeJwt(((DtoGetList)req.getData()).getToken());
        User user = urji.findOneById(reqUser.getId()).get();
        Cart cart = crji.getCartByUserId(user.getId());
        List<Product> dbProducts = cart.getProductList();
        for (Product p : dbProducts) {
            products.add(DtoProductBuilder.aDtoProduct()
                    .withName(p.getName())
                    .withCost(p.getCost())
                    .withId(p.getId())
                    .withDescription(p.getDescription())
                    .build());
        }
        return new DtoProducts(products, prji.getCount());
    }
}

