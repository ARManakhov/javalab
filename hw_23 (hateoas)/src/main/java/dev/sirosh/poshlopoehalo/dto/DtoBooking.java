package dev.sirosh.poshlopoehalo.dto;

import dev.sirosh.poshlopoehalo.model.Booking;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DtoBooking {
    Long userId;
    Long movementId;
    public static DtoBooking getDto(Booking booking){
        return DtoBooking.builder()
                .movementId(booking.getMovement().getId())
                .userId(booking.getUser().getId())
                .build();
    }
}
