package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.itis.dto.DtoPostComment;
import ru.itis.models.Post;
import ru.itis.models.PostComment;
import ru.itis.models.Subscription;
import ru.itis.models.User;
import ru.itis.security.details.UserDetailsImpl;
import ru.itis.services.SubscriptionService;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class SubscriptionsController {

    @Autowired
    SubscriptionService subscriptionService;

    @RequestMapping(value = "/subscribe/{id}", method = RequestMethod.POST)
    String newSubscribtion(Authentication authentication, @PathVariable("id") Long userid) {
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        subscriptionService.subscribe(Subscription.builder()
                .author(User.builder().id(userid).build())
                .subscriber(user)
                .build());
        return "redirect:/profile/" + userid;
    }

    @RequestMapping(value = "/feed", method = RequestMethod.GET)
    String getFeed(Authentication authentication, Model model) {
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        List<Post> posts = new ArrayList<>();
        for (Subscription sub : user.getSubscriptions()) {
            posts.addAll(sub.getAuthor().getPosts());
        }
        List<Post> postsSorted = posts.stream().sorted((p1, p2) -> (int) (p1.getId() - p2.getId())).collect(Collectors.toList());
        model.addAttribute("posts", postsSorted);
        model.addAttribute("navUser", user);
        return "feed";
    }
}
