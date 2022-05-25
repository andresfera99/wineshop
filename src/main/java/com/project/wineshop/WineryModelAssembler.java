package com.project.wineshop;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
class WineryModelAssembler implements RepresentationModelAssembler<Winery, EntityModel<Winery>> {

    @Override
    public EntityModel<Winery> toModel(Winery winery) {

        return EntityModel.of(winery, //
                linkTo(methodOn(WineryController.class).one(winery.getId())).withSelfRel(),
                linkTo(methodOn(WineryController.class).all()).withRel("wineries"));
    }
}