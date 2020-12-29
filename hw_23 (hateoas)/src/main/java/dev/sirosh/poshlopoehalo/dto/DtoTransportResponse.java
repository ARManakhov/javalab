package dev.sirosh.poshlopoehalo.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
public class DtoTransportResponse  extends RepresentationModel<DtoTransportResponse>{
    String status;
    DtoTransport dtoTransport;
}
