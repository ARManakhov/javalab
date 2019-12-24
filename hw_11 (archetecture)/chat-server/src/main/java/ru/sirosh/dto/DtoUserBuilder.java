package ru.sirosh.dto;

public final class DtoUserBuilder {
    private String username;
    private String password;
    private Long id;
    private String role;
    private String token;

    private DtoUserBuilder() {
    }

    public static DtoUserBuilder aDtoUser() {
        return new DtoUserBuilder();
    }

    public DtoUserBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public DtoUserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public DtoUserBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public DtoUserBuilder withRole(String role) {
        this.role = role;
        return this;
    }

    public DtoUserBuilder withToken(String token) {
        this.token = token;
        return this;
    }

    public DtoUser build() {
        DtoUser dtoUser = new DtoUser();
        dtoUser.setUsername(username);
        dtoUser.setPassword(password);
        dtoUser.setId(id);
        dtoUser.setRole(role);
        dtoUser.setToken(token);
        return dtoUser;
    }
}
