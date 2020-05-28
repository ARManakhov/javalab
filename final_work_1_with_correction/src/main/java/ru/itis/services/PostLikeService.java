package ru.itis.services;

import ru.itis.models.Post;
import ru.itis.models.PostComment;
import ru.itis.models.PostLike;

public interface PostLikeService {
    boolean likePress(PostLike postLike);
}
