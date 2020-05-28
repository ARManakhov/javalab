package ru.itis.repositories;

import ru.itis.models.Post;

import javax.sound.sampled.Port;

public abstract class PostRepository extends CrudRepository<Post, Long> {
    public PostRepository() {
        super(Post.class);
    }
}
