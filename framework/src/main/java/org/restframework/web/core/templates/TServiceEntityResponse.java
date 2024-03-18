package org.restframework.web.core.templates;

import org.restframework.web.annotations.Template;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@Template(
        templateName="Service",
        rule= SpringComponents.SERVICE,
        generics={"UUID"},
        type=ClassTypes.CLASS
)
@SuppressWarnings("unused")
public interface TServiceEntityResponse<ID, DTO, Model extends ModelFrame<ID>> {
    ServiceTemplateUtils utils = new ServiceTemplateUtils();
    ResponseEntity<Integer> insert(DTO dto);
    ResponseEntity<List<DTO>> getAll();
    ResponseEntity<Boolean> removeById(ID id);
    ResponseEntity<Boolean> update(ID id, Model entity);
    default ResponseEntity<Optional<Boolean>> remove(Model entity) {
        return ResponseEntity.noContent().build();
    }
    default ResponseEntity<Optional<Model>> getById(ID id) { return ResponseEntity.noContent().build(); }
    default ResponseEntity<Optional<Boolean>> removeAll() {
        return ResponseEntity.noContent().build();
    }
}
