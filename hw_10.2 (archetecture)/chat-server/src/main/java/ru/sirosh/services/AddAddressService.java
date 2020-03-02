package ru.sirosh.services;

import ru.sirosh.TokenizeUser;
import ru.sirosh.context.Component;
import ru.sirosh.dto.Dto;
import ru.sirosh.dto.DtoAddress;
import ru.sirosh.dto.DtoAddressBuilder;
import ru.sirosh.dto.DtoAddresses;
import ru.sirosh.models.Address;
import ru.sirosh.models.AddressBuilder;
import ru.sirosh.models.User;
import ru.sirosh.network.SocketsManager;
import ru.sirosh.protocol.Request;
import ru.sirosh.repositories.AddressRepositoryJdbcImpl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class AddAddressService implements Component {

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
        return "add_address";
    }

    @Override
    public Dto execute(Request req) {
        AddressRepositoryJdbcImpl arji = new AddressRepositoryJdbcImpl(dbConnection);
        List<DtoAddress> dtoAddresses = new ArrayList<>();
        DtoAddress dtoAddress = (DtoAddress) req.getData();
        User reqUser = new TokenizeUser().decodeJwt(dtoAddress.getToken());
        arji.save(AddressBuilder.anAddress()
                .withId(dtoAddress.getId())
                .withDescription(dtoAddress.getDescription())
                .withUserId(reqUser.getId())
                .build());
        List<Address> dbAddresses = arji.findByUserId(reqUser.getId());
        for (Address dbAddress : dbAddresses) {
            dtoAddresses.add(DtoAddressBuilder.aDtoAddress()
                    .withDescription(dbAddress.getDescription())
                    .withId(dbAddress.getId())
                    .build());
        }
        return new DtoAddresses(dtoAddresses, dbAddresses.size());
    }
}