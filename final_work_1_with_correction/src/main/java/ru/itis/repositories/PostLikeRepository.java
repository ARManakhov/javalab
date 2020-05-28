package ru.itis.repositories;

import ru.itis.models.PostLike;

import java.util.Optional;

public abstract class PostLikeRepository extends CrudRepository<PostLike, Long> {
    public PostLikeRepository() {
        super(PostLike.class);
    }

    public abstract Optional<PostLike> findByEntity(PostLike postLike);
}
