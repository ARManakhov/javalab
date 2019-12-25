package ru.sirosh.services;

import ru.sirosh.TokenizeUser;
import ru.sirosh.context.Component;
import ru.sirosh.dto.*;
import ru.sirosh.models.*;
import ru.sirosh.network.SocketsManager;
import ru.sirosh.protocol.Request;
import ru.sirosh.repositories.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class NewOrderService implements Component {

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
        return "new_order";
    }

    @Override
    public Dto execute(Request req) {
        OrderRepositoryJdbcImpl orji = new OrderRepositoryJdbcImpl(dbConnection);
        AddressRepositoryJdbcImpl arji = new AddressRepositoryJdbcImpl(dbConnection);
        CartRepositoryJdbcImpl crji = new CartRepositoryJdbcImpl(dbConnection);
        DtoAddress dtoAddress = (DtoAddress) req.getData();
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
