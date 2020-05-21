package ru.itis.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.dto.DtoPostComment;
import ru.itis.dto.DtoPost;
import ru.itis.models.Post;
import ru.itis.models.PostComment;
import ru.itis.models.User;
import ru.itis.security.details.UserDetailsImpl;
import ru.itis.services.PostService;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(path = "api", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestPostContorller {
    @Autowired
    PostService postService;
    @RequestMapping(value = "/post", method = RequestMethod.POST)
    ResponseEntity newPost(Authentication authentication, DtoPost dtoPost) {
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        Post post = Post.builder().header(dtoPost.getHeader()).text(dtoPost.getText()).author(user).build();
        postService.addPost(post);
        Map<Object, Object> model = new HashMap<>();
        model.put("status","ok");
        return ok(model);
    }

    @RequestMapping(value = "/post_comment/{id}", method = RequestMethod.POST)
    ResponseEntity newPostComment(Authentication authentication, DtoPostComment postComment, @PathVariable("id") Long id) {
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        PostComment comment = PostComment.builder().author(user).post(Post.builder().id(id).build()).text(postComment.getText()).build();
        postService.addComment(comment);
        Map<Object, Object> model = new HashMap<>();
        model.put("status","ok");
        return ok(model);    }
}
