package dev.sirosh.poshlopoehalo.representationProcessor;

import dev.sirosh.poshlopoehalo.controller.api.RestCityController;
import dev.sirosh.poshlopoehalo.controller.api.RestUserProfileController;
import dev.sirosh.poshlopoehalo.dto.DtoCityResponse;
import dev.sirosh.poshlopoehalo.dto.DtoUserResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import static java.util.Objects.nonNull;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DtoUserResponseProcessor implements RepresentationModelProcessor<EntityModel<DtoUserResponse>> {
    @Override
    public EntityModel<DtoUserResponse> process(EntityModel<DtoUserResponse> model) {
        DtoUserResponse dtoUserResponse = model.getContent();
        if (nonNull(dtoUserResponse) && dtoUserResponse.getStatus().equals("ok")) {
            model.add(linkTo(methodOn(RestUserProfileController.class)).withRel("publish"));
        }
        return model;
    }
}
