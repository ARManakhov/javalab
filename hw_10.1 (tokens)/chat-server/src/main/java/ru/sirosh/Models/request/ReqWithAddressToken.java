package ru.sirosh.Models.request;

import ru.sirosh.Models.Address;

public class ReqWithAddressToken extends DummyRequest{
    public Address address;
    public String token;
    private ReqWithAddressToken() {
    }

    public ReqWithAddressToken(String header, Address address) {
        super(header);
        this.address = address;
    }

    public ReqWithAddressToken(String header, Address address, String token) {
        super(header);
        this.address = address;
        this.token = token;
    }
}
