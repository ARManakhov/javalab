package ru.itis.services;

import ru.itis.models.Post;
import ru.itis.models.PostComment;

import java.awt.*;

public interface PostService {
    boolean addPost(Post post);

    boolean addComment(PostComment postComment);
}
