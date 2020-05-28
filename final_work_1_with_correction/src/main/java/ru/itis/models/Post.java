package ru.itis.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "posts")
public class Post{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String text;
    String header;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authorId", nullable = false)
    User author;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "post")
    List<PostComment> comments;

}
