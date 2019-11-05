package ru.sirosh.Models;

public class User {
    public String username;
    public String password;
    public long id;

    public User(long id, String username, String password) {
        this.id=id;
        this.username=username;
        this.password=password;
    }
    public User(String username, String password) {
        this.username=username;
        this.password=password;
    }
}
