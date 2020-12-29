package dev.sirosh.poshlopoehalo.controller;

import dev.sirosh.poshlopoehalo.dto.DtoTransport;
import dev.sirosh.poshlopoehalo.model.Transport;
import dev.sirosh.poshlopoehalo.service.TransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TransportController {
    @Autowired
    TransportService transportService;

    @GetMapping("/new_transport")
    String getNewTransportPage(){
        return "new_transport";
    }

    @PostMapping("/new_transport")
    String getNewTransportMethod(DtoTransport dtoTransport){
        Transport transport = Transport.builder().model(dtoTransport.getModel()).description(dtoTransport.getDescription()).transportType(dtoTransport.getTransportType()).build();
        transportService.add(transport);
        return "new_transport";
    }
}
