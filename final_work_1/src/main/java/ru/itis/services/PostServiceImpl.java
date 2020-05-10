package ru.itis.services;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.models.Post;
import ru.itis.models.PostComment;
import ru.itis.repositories.PostCommentRepository;
import ru.itis.repositories.PostRepository;

import java.awt.*;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostCommentRepository commentRepository;

    @Override
    public boolean addPost(Post post) {
        postRepository.save(post);
        return false;
    }

    @Override
    public boolean addComment(PostComment postComment) {
        commentRepository.save(postComment);
        return false;
    }
}
