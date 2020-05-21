package ru.itis.services;

import org.springframework.beans.factory.annotation.Autowired;
import ru.itis.models.PostLike;
import ru.itis.repositories.PostLikeRepository;

import java.util.Optional;

public class PostLikeServiceImpl implements PostLikeService {

    @Autowired
    PostLikeRepository postLikeRepository;
    @Override
    public boolean likePress(PostLike postLike) {
        Optional<PostLike> postLikeOptional= postLikeRepository.findByEntity(postLike);
        if (postLikeOptional.isPresent()){
            postLikeRepository.delete(postLike);
            return true;
        } else {
            postLikeRepository.save(postLike);
            return false;
        }
    }
}
