package org.example;

import org.restframework.web.annotations.Template;
import org.restframework.web.core.templates.ClassTypes;
import org.restframework.web.core.templates.ModelFrame;
import org.restframework.web.core.templates.SpringComponents;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Template(
        templateName = "Service",
        rule = SpringComponents.SERVICE,
        generics = "UUID",
        type = ClassTypes.CLASS
)
public interface NewServiceTemplate<ID, DTO, Model extends ModelFrame<ID>> {
    ResponseEntity<List<DTO>> findAll();
    ResponseEntity<Integer> save(Model entity);
}
