package dev.sirosh.poshlopoehalo.controller.api;

import dev.sirosh.poshlopoehalo.dto.DtoMovementExt;
import dev.sirosh.poshlopoehalo.dto.DtoUser;
import dev.sirosh.poshlopoehalo.model.Booking;
import dev.sirosh.poshlopoehalo.model.User;
import dev.sirosh.poshlopoehalo.security.details.UserDetailsImpl;
import dev.sirosh.poshlopoehalo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class RestProfileController {
    @Autowired
    UserService userService;

    @GetMapping("/api/profile/")
    ResponseEntity getProfile(Authentication authentication) {
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        Optional<User> userO = userService.getUser(user.getId());
        Map<Object, Object> model = new HashMap<>();
        if (userO.isPresent()) {
            return ok(DtoUser.getDto(userO.get()));
        }
        return ok(model);

    }

    @GetMapping("/api/profile/{id}")
    ResponseEntity getProfileById(@PathVariable Long id) {
        Optional<User> userO = userService.getUser(id);
        Map<Object, Object> model = new HashMap<>();
        if (userO.isPresent()) {
            return ok(DtoUser.getDto(userO.get()));
        }
        return ok(model);

    }

    @GetMapping("/api/profile/{id}/booking")
    ResponseEntity getProfileBooking(@PathVariable Long id) {
        Optional<User> userO = userService.getUser(id);
        Map<Object, Object> model = new HashMap<>();
        if (userO.isPresent()) {
            List<Booking> bookings = userO.get().getBookings();
            List<DtoMovementExt> movementList = new ArrayList<>();
            for (Booking booking : bookings) {
                movementList.add(DtoMovementExt.toDto(booking.getMovement()));
            }
            return ok(movementList);
        }
        return ok(model);

    }

}
