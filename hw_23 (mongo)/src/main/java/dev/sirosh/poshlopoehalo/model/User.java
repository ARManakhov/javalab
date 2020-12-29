package dev.sirosh.poshlopoehalo.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class User {
    long id;
    String name;
    String mail;
    String hashPassword;
    LocalDateTime createdAt;
}
