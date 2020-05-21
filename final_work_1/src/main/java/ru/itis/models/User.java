package ru.itis.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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
    String email;
    String hashPassword;
    Role role;
    State state;
    LocalDateTime createdAt;
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "author")
    List<Post> posts;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
    List<PostComment> comments;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    List<PostLike> likes;


}
