package dev.sirosh.poshlopoehalo.representationProcessor;

import dev.sirosh.poshlopoehalo.controller.api.RestBookingController;
import dev.sirosh.poshlopoehalo.controller.api.RestCityController;
import dev.sirosh.poshlopoehalo.dto.BookingResponseDto;
import dev.sirosh.poshlopoehalo.dto.DtoCityResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;

import static java.util.Objects.nonNull;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class DtoCityResponseProcessor implements RepresentationModelProcessor<EntityModel<DtoCityResponse>> {

    @Override
    public EntityModel<DtoCityResponse> process(EntityModel<DtoCityResponse> model) {
        DtoCityResponse dtoCityResponse = model.getContent();
        if (nonNull(dtoCityResponse) && dtoCityResponse.getStatus().equals("ok")) {
            model.add(linkTo(methodOn(RestCityController.class)).withRel("publish"));
        }
        return model;
    }
}
