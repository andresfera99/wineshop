package com.project.wineshop;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
class TypeController {

    private final TypeRepository repository;
    private final TypeModelAssembler assembler;

    TypeController(TypeRepository repository, TypeModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }


    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/types")
    CollectionModel<EntityModel<Type>> all() {

        List<EntityModel<Type>> types = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(types, linkTo(methodOn(TypeController.class).all()).withSelfRel());


    }
    // end::get-aggregate-root[]

    @PostMapping("/types")
    ResponseEntity<?> newType(@RequestBody Type newType) {

        EntityModel<Type> entityModel = assembler.toModel(repository.save(newType));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    // Single item

    @GetMapping("/types/{id}")
    EntityModel<Type> one(@PathVariable Integer id) {

        Type type = repository.findById(id)
                .orElseThrow(() -> new TypeNotFoundException(id));
        return assembler.toModel(type);
    }

    @PutMapping("/types/{id}")
    ResponseEntity<?> replaceType(@RequestBody Type newType, @PathVariable Integer id) {

        Type updatedType = repository.findById(id) //
                .map(type -> {
                    type.setName(newType.getName());
                    return repository.save(type);
                }) //
                .orElseGet(() -> {
                    newType.setId(id);
                    return repository.save(newType);
                });

        EntityModel<Type> entityModel = assembler.toModel(updatedType);

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/types/{id}")
    ResponseEntity<?> deleteType(@PathVariable Integer id) {

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
