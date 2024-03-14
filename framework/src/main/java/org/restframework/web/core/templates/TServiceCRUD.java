package org.restframework.web.core.templates;

import org.restframework.web.annotations.Template;

import java.util.List;
import java.util.Optional;

@Template(
        templateName="Service",
        rule= SpringComponents.SERVICE,
        generics={"UUID"},
        type=ClassTypes.CLASS
)
@SuppressWarnings("unused")
public interface TServiceCRUD<ID, DTO, Model extends ModelFrame<ID>> {
    ServiceTemplateUtils utils = new ServiceTemplateUtils();
    int insert(DTO dto);
    List<DTO> getAll();
    boolean removeById(ID id);
    boolean update(ID id, Model entity);
    default Optional<Boolean> remove(Model entity) {
        return Optional.empty();
    }
    default Optional<Model> getById(ID id) { return Optional.empty(); }
    default Optional<Boolean> removeAll() {
        return Optional.empty();
    }
}
