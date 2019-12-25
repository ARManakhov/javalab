package ru.sirosh.services;

import ru.sirosh.TokenizeUser;
import ru.sirosh.context.Component;
import ru.sirosh.dto.*;
import ru.sirosh.models.Address;
import ru.sirosh.models.User;
import ru.sirosh.network.SocketsManager;
import ru.sirosh.protocol.Request;
import ru.sirosh.repositories.AddressRepositoryJdbcImpl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class GetAddressesService implements Component {

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
        return "get_addresses";
    }

    @Override
    public Dto execute(Request req) {
        AddressRepositoryJdbcImpl arji = new AddressRepositoryJdbcImpl(dbConnection);
        List<DtoAddress> dtoAddresses = new ArrayList<>();
        DtoGetList dtoGetList = (DtoGetList) req.getData();
        User reqUser = new TokenizeUser().decodeJwt(dtoGetList.getToken());
        List<Address> dbAddresses = arji.findByUserId(reqUser.getId());
        for (Address dbAddress : dbAddresses) {
            dtoAddresses.add(DtoAddressBuilder.aDtoAddress()
            .withDescription(dbAddress.getDescription())
            .withId(dbAddress.getId())
            .build());
        }
        return new DtoAddresses(dtoAddresses,dbAddresses.size());
    }
}