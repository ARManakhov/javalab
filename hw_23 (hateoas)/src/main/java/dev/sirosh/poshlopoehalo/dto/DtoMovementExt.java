package dev.sirosh.poshlopoehalo.dto;

import dev.sirosh.poshlopoehalo.model.City;
import dev.sirosh.poshlopoehalo.model.Movement;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class DtoMovementExt {
    Long id;
    DtoTransport transport;
    DtoCity cityFrom;
    DtoCity cityTo;
    Date departureDate;
    Date arrivalDate;
    Double price;

    static public DtoMovementExt toDto(Movement movement){
        return DtoMovementExt.builder()
                .arrivalDate(movement.getArrivalDate())
                .departureDate(movement.getDepartureDate())
                .cityFrom(DtoCity.toDto(movement.getFrom()))
                .cityTo(DtoCity.toDto(movement.getTo()))
                .transport(DtoTransport.toDto(movement.getTransport()))
                .price(movement.getPrice())
                .id(movement.getId())
                .build();
    }
}
