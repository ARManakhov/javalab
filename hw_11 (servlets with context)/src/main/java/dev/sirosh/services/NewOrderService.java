package dev.sirosh.services;

import dev.sirosh.TokenizeUser;
import dev.sirosh.dto.Dto;
import dev.sirosh.dto.DtoAddress;
import dev.sirosh.dto.DtoStatus;
import dev.sirosh.models.*;
import dev.sirosh.repositories.AddressRepositoryJdbcImpl;
import dev.sirosh.repositories.CartRepositoryJdbcImpl;
import dev.sirosh.repositories.OrderRepositoryJdbcImpl;

import java.sql.Connection;

public class NewOrderService {

    Connection dbConnection;


    public NewOrderService(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public Dto execute( DtoAddress dtoAddress) {
        OrderRepositoryJdbcImpl orji = new OrderRepositoryJdbcImpl(dbConnection);
        AddressRepositoryJdbcImpl arji = new AddressRepositoryJdbcImpl(dbConnection);
        CartRepositoryJdbcImpl crji = new CartRepositoryJdbcImpl(dbConnection);
        User reqUser = new TokenizeUser().decodeJwt(dtoAddress.getToken());
        Cart cart = crji.getCartByUserId(reqUser.getId());
        Address address = arji.find((int) dtoAddress.getId()).get();
        Order order = OrderBuilder.anOrder()
                .withAddressId(address.getId())
                .withUserId(reqUser.getId())
                .withProductList(cart.getProductList())
                .build();
        orji.save(order);
        crji.delAllProducts(cart);
        return new DtoStatus("success");
    }
}
