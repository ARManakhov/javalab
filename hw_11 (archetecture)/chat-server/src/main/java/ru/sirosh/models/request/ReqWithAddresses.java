package ru.sirosh.models.request;

import ru.sirosh.models.Address;

import java.util.List;

public class ReqWithAddresses extends DummyRequest{
    public List<Address> address;

    private ReqWithAddresses() {
    }

    public ReqWithAddresses(String header, List<Address> address) {
        super(header);
        this.address = address;
    }
}