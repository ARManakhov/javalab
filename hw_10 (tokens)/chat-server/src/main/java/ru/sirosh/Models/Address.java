package ru.sirosh.Models;

public class Address {
    private Address(){}
    public long id;
    public String description;
    public long userId;

    public Address(long id, String description, long userId) {
        this.id = id;
        this.description = description;
        this.userId = userId;
    }
}
