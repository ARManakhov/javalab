package dev.sirosh.services;

import dev.sirosh.TokenizeUser;
import dev.sirosh.dto.*;
import dev.sirosh.models.Cart;
import dev.sirosh.models.Product;
import dev.sirosh.models.User;
import dev.sirosh.repositories.CartRepositoryJdbcImpl;
import dev.sirosh.repositories.ProductRepositoryJdbcImpl;
import dev.sirosh.repositories.UsersRepository;
import dev.sirosh.repositories.UsersRepositoryJdbcImpl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class GetCartService {
    Connection dbConnection;


    public GetCartService(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public Dto execute(DtoGetList dtoGetList) {
        ProductRepositoryJdbcImpl prji = new ProductRepositoryJdbcImpl(dbConnection);
        UsersRepository urji = new UsersRepositoryJdbcImpl(dbConnection);
        List<DtoProduct> products = new ArrayList<>();
        CartRepositoryJdbcImpl crji = new CartRepositoryJdbcImpl(dbConnection);
        User reqUser = new TokenizeUser().decodeJwt(dtoGetList.getToken());
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

