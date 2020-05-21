package ru.itis.repositories;

import ru.itis.models.Post;
import ru.itis.models.PostLike;

import java.util.Optional;

public interface PostLikeRepository extends CrudRepository<PostLike, Long> {
    Optional<PostLike> findByEntity(PostLike postLike);
}
