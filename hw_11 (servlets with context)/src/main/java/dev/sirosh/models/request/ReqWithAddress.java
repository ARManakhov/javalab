package dev.sirosh.models.request;

import dev.sirosh.models.Address;

public class ReqWithAddress extends DummyRequest{
    public Address address;

    private ReqWithAddress() {
    }

    public ReqWithAddress(String header, Address address) {
        super(header);
        this.address = address;
    }
}
