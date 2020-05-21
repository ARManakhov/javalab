package ru.itis.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.dto.DtoUser;
import ru.itis.models.User;
import ru.itis.security.details.UserDetailsImpl;
import ru.itis.services.UsersService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(path = "api", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestUsersController {

    @Autowired
    private UsersService usersService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String getUsers(Model model) {

        model.addAttribute("users", usersService.getUsers());
        return "users";
    }

    @GetMapping("/api/profile/")
    ResponseEntity getProfile(Authentication authentication) {
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        Optional<User> userO = usersService.getUser(user.getId());
        Map<Object, Object> model = new HashMap<>();
        if (userO.isPresent()) {
            return ok(DtoUser.getDto(userO.get()));
        }
        return ok(model);

    }

    @GetMapping("/api/profile/{id}")
    ResponseEntity getProfileById(@PathVariable Long id) {
        Optional<User> userO = usersService.getUser(id);
        Map<Object, Object> model = new HashMap<>();
        if (userO.isPresent()) {
            return ok(DtoUser.getDto(userO.get()));
        }
        return ok(model);

    }


}
