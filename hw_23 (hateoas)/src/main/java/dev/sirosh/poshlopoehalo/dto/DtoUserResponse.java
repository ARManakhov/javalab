package dev.sirosh.poshlopoehalo.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
public class DtoUserResponse extends RepresentationModel<DtoUserResponse> {
    String status;
    DtoUser dtoUser;
}
