package dev.sirosh.poshlopoehalo.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String name;
    String mail;
    String hashPassword;
    Role role;
    State state;
    LocalDateTime createdAt;
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "user")
    List<Booking> bookings;
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user")
    List<ValidationToken> validationTokens;
}
