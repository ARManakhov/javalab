package ru.itis.servlets.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.servlets.models.State;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class User {
    long id;
    String name;
    String email;
    String hashPassword;
    Role role;
    State state;
    LocalDateTime createdAt;
}
