package ru.itis.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String name;
    String email;
    String hashPassword;
    Role role;
    State state;
    LocalDateTime createdAt;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "author")
    List<Post> posts;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
    List<PostComment> comments;
}
