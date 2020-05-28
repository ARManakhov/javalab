package ru.itis.models;

import javafx.geometry.Pos;
import lombok.*;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "posts_comment")
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
