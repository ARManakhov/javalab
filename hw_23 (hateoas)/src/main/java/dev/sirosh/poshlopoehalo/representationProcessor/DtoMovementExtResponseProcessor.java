package dev.sirosh.poshlopoehalo.representationProcessor;

import dev.sirosh.poshlopoehalo.controller.api.RestCityController;
import dev.sirosh.poshlopoehalo.controller.api.RestMovementController;
import dev.sirosh.poshlopoehalo.dto.DtoCityResponse;
import dev.sirosh.poshlopoehalo.dto.DtoMovementExt;
import dev.sirosh.poshlopoehalo.dto.DtoMovementExtResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;

import static java.util.Objects.nonNull;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class DtoMovementExtResponseProcessor implements RepresentationModelProcessor<EntityModel<DtoMovementExtResponse>> {
    @Override
    public EntityModel<DtoMovementExtResponse> process(EntityModel<DtoMovementExtResponse> model) {
        DtoMovementExtResponse dtoMovementExt = model.getContent();
        if (nonNull(dtoMovementExt) && dtoMovementExt.getStatus().equals("ok")) {
            model.add(linkTo(methodOn(RestMovementController.class)).withRel("publish"));
        }
        return model;
    }
}
