package dev.sirosh.models;

public final class AddressBuilder {
    private long id;
    private String description;
    private long userId;

    private AddressBuilder() {
    }

    public static AddressBuilder anAddress() {
        return new AddressBuilder();
    }

    public AddressBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public AddressBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public AddressBuilder withUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public Address build() {
        Address address = new Address();
        address.setId(id);
        address.setDescription(description);
        address.setUserId(userId);
        return address;
    }
}
