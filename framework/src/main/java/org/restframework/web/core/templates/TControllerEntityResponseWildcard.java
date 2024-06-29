package org.restframework.web.core.templates;

import org.restframework.web.annotations.Template;
import org.restframework.web.exceptions.ExceptionAdvice;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@Template(
        templateName="Controller",
        rule= SpringComponents.CONTROLLER,
        type=ClassTypes.CLASS
)
@SuppressWarnings("unused")
public interface TControllerEntityResponseWildcard<ID, DTO, Model> extends ExceptionAdvice {
    ResponseEntity<?> insertEntity(DTO entity);
    ResponseEntity<?> getAllEntities();
    ResponseEntity<?> removeEntityById(ID id);
    ResponseEntity<?> updateEntity(ID id, Model entity);
    default ResponseEntity<?> getById(ID id) { return ResponseEntity.noContent().build(); }
    default ResponseEntity<?> removeEntity(Model entityToRemove) { return ResponseEntity.noContent().build(); }
    default ResponseEntity<?> removeAllEntities() { return ResponseEntity.noContent().build(); }
}
