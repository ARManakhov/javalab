package dev.sirosh.poshlopoehalo.dto;

import dev.sirosh.poshlopoehalo.model.Booking;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
public class BookingResponseDto extends RepresentationModel<AuthResponseDto> {
        String status;
        Booking booking;
}
