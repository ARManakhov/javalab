package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.itis.dto.DtoPostComment;
import ru.itis.dto.DtoPost;
import ru.itis.models.Post;
import ru.itis.models.PostComment;
import ru.itis.models.User;
import ru.itis.security.details.UserDetailsImpl;
import ru.itis.services.PostService;

import javax.validation.Valid;

@Controller
public class PostContorller {
    @Autowired
    PostService postService;

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    String newPost(Authentication authentication, @Valid DtoPost dtoPost) {
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        Post post = Post.builder().header(dtoPost.getHeader()).text(dtoPost.getText()).author(user).build();
        postService.addPost(post);
        return "redirect:/profile";
    }

    @RequestMapping(value = "/post_comment/{id}", method = RequestMethod.POST)
    String newPostComment(Authentication authentication, @Valid DtoPostComment postComment, @PathVariable("id") Long id) {
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        PostComment comment = PostComment.builder().author(user).post(Post.builder().id(id).build()).text(postComment.getText()).build();
        postService.addComment(comment);
        return "redirect:/profile";
    }

    @RequestMapping(value = "/post_delete/{id}", method = RequestMethod.POST)
    String deletePost(Authentication authentication, @PathVariable Long id) {
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        postService.delete(id, user);
        return "redirect:/profile";

    }
}
