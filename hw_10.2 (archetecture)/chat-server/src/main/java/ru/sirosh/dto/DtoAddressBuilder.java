package ru.sirosh.dto;

public final class DtoAddressBuilder {
    public long id;
    public String description;
    public long userId;
    public String token;

    private DtoAddressBuilder() {
    }

    public static DtoAddressBuilder aDtoAddress() {
        return new DtoAddressBuilder();
    }

    public DtoAddressBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public DtoAddressBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public DtoAddressBuilder withUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public DtoAddressBuilder withToken(String token) {
        this.token = token;
        return this;
    }

    public DtoAddress build() {
        DtoAddress dtoAddress = new DtoAddress();
        dtoAddress.setId(id);
        dtoAddress.setDescription(description);
        dtoAddress.setUserId(userId);
        dtoAddress.setToken(token);
        return dtoAddress;
    }
}
