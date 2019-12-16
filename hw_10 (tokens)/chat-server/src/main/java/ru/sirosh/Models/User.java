package ru.sirosh.Models;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.*;


public class User {

    private User() {

    }

    private String username;
    private String password;
    private Long id;
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public User(String username, String password, Long id, String role) {
        this.username = username;
        this.password = password;
        this.id = id;
        this.role = role;
    }

    public User(String username, Long id, String role) {
        this.username = username;
        this.id = id;
        this.role = role;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username) &&
                id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, id);
    }
}