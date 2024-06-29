package org.restframework.web.core.templates;

import org.restframework.web.annotations.Template;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@Template(
        templateName="Controller",
        rule= SpringComponents.CONTROLLER,
        type=ClassTypes.CLASS
)
@SuppressWarnings("unused")
public interface TControllerBasic {
    ServiceTemplateUtils utils = new ServiceTemplateUtils();
    <DTO> ResponseEntity<?> insert(DTO dto);
    ResponseEntity<List<?>> getAll();
    <ID> ResponseEntity<?> removeById(ID id);
    <ID, REQ> ResponseEntity<Boolean> update(ID id, REQ entity);
    default <REQ>  ResponseEntity<Optional<Boolean>> remove(REQ entity) {
        return ResponseEntity.noContent().build();
    }
    default <ID> ResponseEntity<Optional<?>> getById(ID id) { return ResponseEntity.noContent().build(); }
    default ResponseEntity<Optional<Boolean>> removeAll() {
        return ResponseEntity.noContent().build();
    }
}
