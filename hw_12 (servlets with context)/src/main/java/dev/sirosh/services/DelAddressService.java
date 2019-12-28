package dev.sirosh.services;

import dev.sirosh.TokenizeUser;
import dev.sirosh.dto.Dto;
import dev.sirosh.dto.DtoAddress;
import dev.sirosh.dto.DtoAddressBuilder;
import dev.sirosh.dto.DtoAddresses;
import dev.sirosh.models.Address;
import dev.sirosh.models.AddressBuilder;
import dev.sirosh.models.User;
import dev.sirosh.repositories.AddressRepositoryJdbcImpl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class DelAddressService {

    Connection dbConnection;


    public DelAddressService(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }


    public Dto execute(DtoAddress dtoAddress) {
        AddressRepositoryJdbcImpl arji = new AddressRepositoryJdbcImpl(dbConnection);
        List<DtoAddress> dtoAddresses = new ArrayList<>();
        User reqUser = new TokenizeUser().decodeJwt(dtoAddress.getToken());
        Address address = arji.find((int) dtoAddress.getId()).get();
        if (address.getUserId() == reqUser.getId()) {
            arji.delete(AddressBuilder.anAddress().withId(dtoAddress.getId()).build());
        }
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