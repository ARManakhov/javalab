package dev.sirosh.poshlopoehalo.representationProcessor;

import dev.sirosh.poshlopoehalo.controller.api.RestCityController;
import dev.sirosh.poshlopoehalo.controller.api.RestTransportController;
import dev.sirosh.poshlopoehalo.dto.DtoCityResponse;
import dev.sirosh.poshlopoehalo.dto.DtoTransportResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import static java.util.Objects.nonNull;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DtoTransportResponseProcessor implements RepresentationModelProcessor<EntityModel<DtoTransportResponse>> {
    @Override
    public EntityModel<DtoTransportResponse> process(EntityModel<DtoTransportResponse> model) {
        DtoTransportResponse dtoTransportResponse = model.getContent();
        if (nonNull(dtoTransportResponse) && dtoTransportResponse.getStatus().equals("ok")) {
            model.add(linkTo(methodOn(RestTransportController.class)).withRel("publish"));
        }
        return model;
    }
}
