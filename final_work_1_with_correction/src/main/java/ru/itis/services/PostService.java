package ru.itis.services;

import ru.itis.models.Post;
import ru.itis.models.PostComment;
import ru.itis.models.User;

import java.awt.*;

public interface PostService {
    boolean addPost(Post post);
    boolean delete(Long id, User user );
    boolean addComment(PostComment postComment);
}
