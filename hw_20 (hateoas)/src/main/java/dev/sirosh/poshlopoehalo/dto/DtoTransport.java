package dev.sirosh.poshlopoehalo.dto;

import dev.sirosh.poshlopoehalo.model.City;
import dev.sirosh.poshlopoehalo.model.Transport;
import dev.sirosh.poshlopoehalo.model.TransportType;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class DtoTransport {
    Long id;
    TransportType transportType;
    String description;
    String model;

    public static DtoTransport toDto(Transport transport) {
        return DtoTransport.builder()
                .id(transport.getId())
                .description(transport.getDescription())
                .model(transport.getModel())
                .transportType(transport.getTransportType())
                .build();
    }

    static Transport toObj(DtoTransport dtoTransport) {
        return Transport.builder()
                .model(dtoTransport.getModel())
                .transportType(dtoTransport.getTransportType())
                .description(dtoTransport.getDescription())
                .id(dtoTransport.getId())
                .build();
    }
}
