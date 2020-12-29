package dev.sirosh.poshlopoehalo.controller;

import dev.sirosh.poshlopoehalo.model.Booking;
import dev.sirosh.poshlopoehalo.model.Movement;
import dev.sirosh.poshlopoehalo.model.User;
import dev.sirosh.poshlopoehalo.security.details.UserDetailsImpl;
import dev.sirosh.poshlopoehalo.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BookingController {
    @Autowired
    BookingService bookingService;

    @PostMapping("/booking/add/{id}")
    @ResponseBody
    String newBooking(@PathVariable Long id, Authentication authentication) {
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        Booking booking = Booking.builder().movement(Movement.builder().id(id).build()).user(user).build();
        if (bookingService.isExists(booking)){
            return "{status:'err'}";
        }
        bookingService.addBooking(booking);
        return "{status:'ok'}";
    }

    @PostMapping("/booking/delete/{id}")
    @ResponseBody
    String delBooking(@PathVariable Long id, Authentication authentication) {
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        bookingService.deleteBooking(Booking.builder().id(id).user(user).build());
        return "redirect:";
    }
}
