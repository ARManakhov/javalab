package ru.itis.models;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.beans.factory.annotation.Autowired;
import ru.itis.services.UsersService;

import javax.persistence.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
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
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "author")
    private List<Post> posts;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
    private List<PostComment> comments;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<PostLike> likes;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "subscriber")
    @Fetch(value = FetchMode.SELECT)
    private List<Subscription> subscriptions;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "author")
    @Fetch(value = FetchMode.SELECT)
    private List<Subscription> subscribers;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", hashPassword='" + hashPassword + '\'' +
                ", role=" + role +
                ", state=" + state +
                ", createdAt=" + createdAt +
                '}';
    }

}
