package dev.sirosh.poshlopoehalo.controller.api;

import dev.sirosh.poshlopoehalo.dto.DtoMovementExt;
import dev.sirosh.poshlopoehalo.dto.DtoUser;
import dev.sirosh.poshlopoehalo.dto.DtoUserResponse;
import dev.sirosh.poshlopoehalo.model.Booking;
import dev.sirosh.poshlopoehalo.model.User;
import dev.sirosh.poshlopoehalo.security.details.UserDetailsImpl;
import dev.sirosh.poshlopoehalo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class RestUserProfileController {
    @Autowired
    UserService userService;

    @GetMapping("/api/profile/")
    ResponseEntity getProfile(Authentication authentication) {
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        Optional<User> userO = userService.getUser(user.getId());
        Map<Object, Object> model = new HashMap<>();
        if (userO.isPresent()) {
            return ok(EntityModel.of(
                    DtoUserResponse.builder()
                    .dtoUser(DtoUser.getDto(userO.get()))
                    .status("ok")
                    .build()));

        }
        return ok(EntityModel.of(
                DtoUserResponse.builder()
                        .status("err")
                        .build()));

    }

    @GetMapping("/api/profile/{id}")
    ResponseEntity getProfileById(@PathVariable Long id) {
        Optional<User> userO = userService.getUser(id);
        if (userO.isPresent()) {
            return ok(EntityModel.of(
                    DtoUserResponse.builder()
                            .dtoUser(DtoUser.getDto(userO.get()))
                            .status("ok")
                            .build()));
        }
        return ok(EntityModel.of(
                DtoUserResponse.builder()
                        .status("err")
                        .build()));
    }

    @GetMapping("/api/profile/{id}/booking")
    ResponseEntity getProfileBooking(@PathVariable Long id) {
        Optional<User> userO = userService.getUser(id);
        if (userO.isPresent()) {
            List<Booking> bookings = userO.get().getBookings();
            List<DtoMovementExt> movementList = new ArrayList<>();
            for (Booking booking : bookings) {
                movementList.add(DtoMovementExt.toDto(booking.getMovement()));
            }
            return ok(movementList);
        }
        return ok(EntityModel.of(
                DtoUserResponse.builder()
                        .status("err")
                        .build()));

    }

}
