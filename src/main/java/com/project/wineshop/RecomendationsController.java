package com.project.wineshop;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
class RecomendationsController {

    private final WineRepository repository;
    private final WineModelAssembler assembler;

    RecomendationsController(WineRepository repository, WineModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/recommend/best")
    CollectionModel<EntityModel<Wine>> best(@RequestParam(name = "top", required = true, defaultValue = "10") Integer num) {

        List<EntityModel<Wine>> wines = repository.findBest(num).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(wines, linkTo(methodOn(WineController.class).all()).withSelfRel());

    }

    @GetMapping("/recommend/expensive")
    CollectionModel<EntityModel<Wine>> expensive(@RequestParam(name = "top", required = true, defaultValue = "10") Integer num) {

        List<EntityModel<Wine>> wines = repository.findExpensive(num).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(wines, linkTo(methodOn(WineController.class).all()).withSelfRel());
    }

    @GetMapping("/recommend/bang")
    CollectionModel<EntityModel<Wine>> bang(@RequestParam(name = "top", required = true, defaultValue = "10") Integer num) {

        List<EntityModel<Wine>> wines = repository.findBang(num).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(wines, linkTo(methodOn(WineController.class).all()).withSelfRel());
    }

    @GetMapping("/recommend/vintage")
    List<String> vintage(@RequestParam(name = "top", required = true, defaultValue = "10") Integer num) {
        /*List<EntityModel<Wine>> wines = repository.findVintage(num).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());*/

        Map<String, List<Wine>> porYear = repository.findVintage(num).stream()
                .collect(Collectors.groupingBy(Wine::getYear));
        return new ArrayList<>(Arrays.asList(porYear.toString()));
    }
}