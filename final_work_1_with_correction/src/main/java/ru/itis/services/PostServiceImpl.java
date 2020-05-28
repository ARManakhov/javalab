package ru.itis.services;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.models.Post;
import ru.itis.models.PostComment;
import ru.itis.models.User;
import ru.itis.repositories.PostCommentRepository;
import ru.itis.repositories.PostRepository;

import java.awt.*;
import java.util.Optional;

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
    public boolean delete(Long id, User user) {
        Optional<Post> byId = postRepository.findById(id);
        if (byId.isPresent() && user.getId() == byId.get().getAuthor().getId()) {
            postRepository.delete(byId.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean addComment(PostComment postComment) {
        commentRepository.save(postComment);
        return false;
    }
}
