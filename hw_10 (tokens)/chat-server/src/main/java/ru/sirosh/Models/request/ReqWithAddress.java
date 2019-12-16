package ru.sirosh.Models.request;

import ru.sirosh.Models.Address;

public class ReqWithAddress extends DummyRequest{
    public Address address;

    private ReqWithAddress() {
    }

    public ReqWithAddress(String header, Address address) {
        super(header);
        this.address = address;
    }
}
