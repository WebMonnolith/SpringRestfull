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
public interface TControllerEntityResponse<ID, DTO, Model> extends ExceptionAdvice {
    ResponseEntity<Integer> insertEntity(DTO entity);
    ResponseEntity<List<DTO>> getAllEntities();
    ResponseEntity<Boolean> removeEntityById(ID id);
    ResponseEntity<Boolean> updateEntity(ID id, Model entity);
    default ResponseEntity<Optional<Model>> getById(ID id) { return ResponseEntity.noContent().build(); }
    default ResponseEntity<Optional<Boolean>> removeEntity(Model entityToRemove) { return ResponseEntity.noContent().build(); }
    default ResponseEntity<Optional<Boolean>> removeAllEntities() { return ResponseEntity.noContent().build(); }
}
