package dev.sirosh.poshlopoehalo.controller.api;

import dev.sirosh.poshlopoehalo.dto.BookingResponseDto;
import dev.sirosh.poshlopoehalo.model.Booking;
import dev.sirosh.poshlopoehalo.model.Movement;
import dev.sirosh.poshlopoehalo.model.User;
import dev.sirosh.poshlopoehalo.security.details.UserDetailsImpl;
import dev.sirosh.poshlopoehalo.service.BookingService;
import dev.sirosh.poshlopoehalo.service.MovementService;
import dev.sirosh.poshlopoehalo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class RestBookingController {
    @Autowired
    UserService userService;

    @Autowired
    MovementService movementService;

    @Autowired
    BookingService bookingService;


    @PostMapping("/api/booking/{id}")
    ResponseEntity addBooking(@PathVariable Long id, Authentication authentication) {
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        Optional<Movement> movementOptional = movementService.get(id);
        if (movementOptional.isPresent()) {
            Booking booking = Booking.builder()
                    .user(user)
                    .movement(movementOptional.get())
                    .build();
            bookingService.addBooking(booking);
            return ok(EntityModel.of(BookingResponseDto.builder()
                    .booking(booking)
                    .status("ok")
                    .build()));
        }
        return ok(EntityModel.of(BookingResponseDto.builder()
                .status("err")
                .build()));
    }

    @DeleteMapping("/api/booking/{id}")
    ResponseEntity deleteBooking(@PathVariable Long id, Authentication authentication) {
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        Optional<Movement> movementOptional = movementService.get(id);
        if (movementOptional.isPresent()) {
            Booking booking = Booking.builder()
                    .user(user)
                    .movement(movementOptional.get())
                    .build();
            bookingService.deleteBooking(booking);
            return ok(EntityModel.of(BookingResponseDto.builder()
                    .booking(booking)
                    .status("ok")
                    .build()));
        }
        return ok(EntityModel.of(BookingResponseDto.builder()
                .status("err")
                .build()));
    }


}
