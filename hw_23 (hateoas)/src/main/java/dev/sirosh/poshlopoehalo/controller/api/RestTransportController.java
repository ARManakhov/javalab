package dev.sirosh.poshlopoehalo.controller.api;

import dev.sirosh.poshlopoehalo.dto.DtoTransport;
import dev.sirosh.poshlopoehalo.dto.DtoTransportResponse;
import dev.sirosh.poshlopoehalo.model.Transport;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class RestTransportController {
    @Autowired
    UserService userService;

    @Autowired
    TransportService transportService;

    @GetMapping("/api/transport/{id}")
    ResponseEntity getTransport(@PathVariable Long id) {
        Optional<Transport> transport = transportService.get(id);
        if (transport.isPresent()) {
            return ok(EntityModel.of(
                    DtoTransportResponse.builder()
                            .dtoTransport(DtoTransport.toDto(transport.get()))
                            .status("ok")
                            .build()));
        }
        return ok(EntityModel.of(
                DtoTransportResponse.builder()
                        .status("err")
                        .build()));
    }

    @PostMapping("/api/transport")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity addTransport(DtoTransport dtoTransport) {

        Transport transport = Transport.builder()
                .model(dtoTransport.getModel())
                .description(dtoTransport.getDescription())
                .transportType(dtoTransport.getTransportType())
                .build();

        transportService.add(transport);

        return ok(EntityModel.of(
                DtoTransportResponse.builder()
                        .dtoTransport(DtoTransport.toDto(transport))
                        .status("ok")
                        .build()));
    }


}
