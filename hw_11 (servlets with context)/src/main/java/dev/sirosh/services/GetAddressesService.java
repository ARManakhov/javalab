package dev.sirosh.services;

import dev.sirosh.TokenizeUser;
import dev.sirosh.dto.*;
import dev.sirosh.models.Address;
import dev.sirosh.models.User;
import dev.sirosh.repositories.AddressRepositoryJdbcImpl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class GetAddressesService {
    Connection dbConnection;


    public GetAddressesService(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public Dto execute(DtoGetList dtoGetList) {
        AddressRepositoryJdbcImpl arji = new AddressRepositoryJdbcImpl(dbConnection);
        List<DtoAddress> dtoAddresses = new ArrayList<>();
        User reqUser = new TokenizeUser().decodeJwt(dtoGetList.getToken());
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