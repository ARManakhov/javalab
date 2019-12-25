package ru.sirosh.services;

import ru.sirosh.TokenizeUser;
import ru.sirosh.context.Component;
import ru.sirosh.dto.*;
import ru.sirosh.models.Cart;
import ru.sirosh.models.Product;
import ru.sirosh.models.ProductBuilder;
import ru.sirosh.models.User;
import ru.sirosh.network.SocketsManager;
import ru.sirosh.protocol.Request;
import ru.sirosh.repositories.CartRepositoryJdbcImpl;
import ru.sirosh.repositories.ProductRepositoryJdbcImpl;
import ru.sirosh.repositories.UsersRepositoryJdbcImpl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class DelProductCartService implements Component {

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
        return "del_to_cart_product";
    }

    @Override
    public Dto execute(Request req) {
        ProductRepositoryJdbcImpl prji = new ProductRepositoryJdbcImpl(dbConnection);
        UsersRepositoryJdbcImpl urji = new UsersRepositoryJdbcImpl(dbConnection);
        CartRepositoryJdbcImpl crji = new CartRepositoryJdbcImpl(dbConnection);
        DtoProduct dtoProduct = (DtoProduct) req.getData();
        User reqUser = new TokenizeUser().decodeJwt(dtoProduct.getToken());
        User user = urji.findOneById(reqUser.getId()).get();
        crji.delProduct(crji.getCartByUserId(user.getId()), ProductBuilder.aProduct().withId(dtoProduct.getId()).build());
        List<DtoProduct> products = new ArrayList<>();
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
        return DtoProductsStateBuilder.aDtoProductsState()
                .withProducts(products)
                .withStatus("success")
                .withTotalCount(prji.getCount())
                .build();
    }
}
