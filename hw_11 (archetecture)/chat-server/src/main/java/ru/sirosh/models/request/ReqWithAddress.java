package ru.sirosh.models.request;

import ru.sirosh.models.Address;

public class ReqWithAddress extends DummyRequest{
    public Address address;

    private ReqWithAddress() {
    }

    public ReqWithAddress(String header, Address address) {
        super(header);
        this.address = address;
    }
}
