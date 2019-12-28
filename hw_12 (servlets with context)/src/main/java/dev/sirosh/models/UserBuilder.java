package dev.sirosh.models;

public final class UserBuilder {
    private String username;
    private String password;
    private Long id;
    private String role;

    private UserBuilder() {
    }

    public static UserBuilder anUser() {
        return new UserBuilder();
    }

    public UserBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public UserBuilder withRole(String role) {
        this.role = role;
        return this;
    }

    public User build() {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setId(id);
        user.setRole(role);
        return user;
    }
}
