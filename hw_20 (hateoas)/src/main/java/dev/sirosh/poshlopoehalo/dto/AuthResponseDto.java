package dev.sirosh.poshlopoehalo.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
public class AuthResponseDto extends RepresentationModel<AuthResponseDto> {
    String status;
    String username;
    String token;
}
