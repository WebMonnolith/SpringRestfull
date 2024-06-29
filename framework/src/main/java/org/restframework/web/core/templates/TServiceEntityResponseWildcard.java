package org.restframework.web.core.templates;

import org.restframework.web.annotations.Template;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@Template(
        templateName="Service",
        rule= SpringComponents.SERVICE,
        type=ClassTypes.CLASS
)
@SuppressWarnings("unused")
public interface TServiceEntityResponseWildcard <ID, DTO, Model> {
    ServiceTemplateUtils utils = new ServiceTemplateUtils();
    ResponseEntity<?> insert(DTO dto);
    ResponseEntity<?> getAll();
    ResponseEntity<?> removeById(ID id);
    ResponseEntity<?> update(ID id, Model entity);
    default ResponseEntity<?> remove(Model entity) {
        return ResponseEntity.noContent().build();
    }
    default ResponseEntity<?> getById(ID id) { return ResponseEntity.noContent().build(); }
    default ResponseEntity<?> removeAll() {
        return ResponseEntity.noContent().build();
    }
}
