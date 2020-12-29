package dev.sirosh.poshlopoehalo.representationProcessor;

import dev.sirosh.poshlopoehalo.controller.BookingController;
import dev.sirosh.poshlopoehalo.controller.api.RestBookingController;
import dev.sirosh.poshlopoehalo.controller.api.RestUserProfileController;
import dev.sirosh.poshlopoehalo.dto.AuthResponseDto;
import dev.sirosh.poshlopoehalo.dto.BookingResponseDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;

import static java.util.Objects.nonNull;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class BookingResponseDtoProcessor implements RepresentationModelProcessor<EntityModel<BookingResponseDto>> {
    @Override
    public EntityModel<BookingResponseDto> process(EntityModel<BookingResponseDto> model) {
        BookingResponseDto bookingResponseDto = model.getContent();
        if (nonNull(bookingResponseDto) && bookingResponseDto.getStatus().equals("ok")){
            model.add(linkTo(methodOn(RestBookingController.class)).withRel("publish"));
        }
        return model;
    }
}
