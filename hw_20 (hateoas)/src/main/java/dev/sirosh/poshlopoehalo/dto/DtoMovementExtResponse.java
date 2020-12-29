package dev.sirosh.poshlopoehalo.dto;

import dev.sirosh.poshlopoehalo.model.Booking;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Builder
@Data
public class DtoMovementExtResponse extends RepresentationModel<AuthResponseDto> {
    String status;
    DtoMovementExt dtoMovementExt;
}