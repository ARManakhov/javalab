package ru.itis.models;

import javafx.geometry.Pos;
import lombok.*;
import org.springframework.stereotype.Service;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class PostComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String text;
    @ManyToOne
    @JoinColumn(name = "postId")
    Post post;
    @ManyToOne
    @JoinColumn(name = "authorId")
    User author;
}
