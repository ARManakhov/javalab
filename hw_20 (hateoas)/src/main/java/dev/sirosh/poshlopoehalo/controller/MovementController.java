package dev.sirosh.poshlopoehalo.controller;

import dev.sirosh.poshlopoehalo.dto.DtoMovement;
import dev.sirosh.poshlopoehalo.model.City;
import dev.sirosh.poshlopoehalo.model.Movement;
import dev.sirosh.poshlopoehalo.model.Transport;
import dev.sirosh.poshlopoehalo.service.CityService;
import dev.sirosh.poshlopoehalo.service.MovementService;
import dev.sirosh.poshlopoehalo.service.TransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Timestamp;
import java.util.Optional;

@Controller
public class MovementController {
    @Autowired
    MovementService movementService;
    @Autowired
    TransportService transportService;
    @Autowired
    CityService cityService;

    @GetMapping("/new_movement")
    String movementNewPage(Model model) {

        model.addAttribute("transports", transportService.getAll());
        model.addAttribute("cites", cityService.getAll());
        return "new_movement";
    }

    @PostMapping("/new_movement")
    String movementNewMethod(DtoMovement dtoMovement) {

        Optional<City> fromO = cityService.get(dtoMovement.getCityFromId());
        Optional<City> toO = cityService.get(dtoMovement.getCityToId());
        Optional<Transport> transportO = transportService.get(dtoMovement.getTransportId());

        if (fromO.isPresent() && toO.isPresent() && transportO.isPresent()) {
            Movement movement = Movement.builder()
                    .from(fromO.get())
                    .to(toO.get())
                    .transport(transportO.get())
                    .departureDate(new Timestamp(dtoMovement.getDepartureDate().getTime()))
                    .arrivalDate(new Timestamp(dtoMovement.getArrivalDate().getTime()))
                    .price(dtoMovement.getPrice())
                    .expired(false)
                    .build();
            movementService.add(movement);
        }
        return "redirect:new_movement";
    }

    @GetMapping("/movement/{id}")
    String movementGetPage(Model model, @PathVariable Long id) {
        Optional<Movement> movementOptional = movementService.get(id);
        movementOptional.ifPresent(movement -> model.addAttribute("movement", movement));
        return "movement";
    }
}
