package dev.sirosh.poshlopoehalo.controller.api;

import dev.sirosh.poshlopoehalo.dto.DtoMovement;
import dev.sirosh.poshlopoehalo.dto.DtoMovementExt;
import dev.sirosh.poshlopoehalo.dto.DtoMovementExtResponse;
import dev.sirosh.poshlopoehalo.model.City;
import dev.sirosh.poshlopoehalo.model.Movement;
import dev.sirosh.poshlopoehalo.model.Transport;
import dev.sirosh.poshlopoehalo.service.CityService;
import dev.sirosh.poshlopoehalo.service.MovementService;
import dev.sirosh.poshlopoehalo.service.TransportService;
import dev.sirosh.poshlopoehalo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class RestMovementController {
    @Autowired
    UserService userService;

    @Autowired
    MovementService movementService;

    @Autowired
    CityService cityService;

    @Autowired
    TransportService transportService;

    @GetMapping("/api/movement/{id}")
    ResponseEntity getMovement(@PathVariable Long id) {
        Optional<Movement> movementOptional = movementService.get(id);
        Map<Object, Object> model = new HashMap<>();
        if (movementOptional.isPresent()) {
            return ok(EntityModel.of(
                    DtoMovementExtResponse.builder()
                            .dtoMovementExt(DtoMovementExt.toDto(movementOptional.get()))
                            .status("ok")
                            .build()));
        }
        return ok(EntityModel.of(
                DtoMovementExtResponse.builder()
                .status("err")
                .build()));
    }

    @PostMapping("/api/movement")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity addMovement(DtoMovement dtoMovement) {
        Map<Object, Object> model = new HashMap<>();
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
            return ok(EntityModel.of(
                    DtoMovementExtResponse.builder()
                            .dtoMovementExt(DtoMovementExt.toDto(movement))
                            .status("ok")
                            .build()));
        }
        return ok(EntityModel.of(
                DtoMovementExtResponse.builder()
                        .status("err")
                        .build()));
    }



}
