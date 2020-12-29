package dev.sirosh.poshlopoehalo.representationProcessor;

import dev.sirosh.poshlopoehalo.controller.api.RestAuthController;
import dev.sirosh.poshlopoehalo.controller.api.RestUserProfileController;
import dev.sirosh.poshlopoehalo.dto.AuthResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import static java.util.Objects.nonNull;

@Component
public class AuthResponseProcessor implements RepresentationModelProcessor<EntityModel<AuthResponseDto>> {

    @Autowired
    private RepositoryEntityLinks links;

    @Override
    public EntityModel<AuthResponseDto> process(EntityModel<AuthResponseDto> model) {
        AuthResponseDto authResponseDto = model.getContent();
        if (nonNull(authResponseDto) && authResponseDto.getStatus().equals("ok")){
            model.add(linkTo(methodOn(RestUserProfileController.class)).withRel("publish"));
        }
        if (nonNull(authResponseDto) && authResponseDto.getStatus().equals("err")){
            model.add(linkTo(methodOn(RestAuthController.class).signUpMethod(null)).withSelfRel());
        }
        return model;
    }
}
