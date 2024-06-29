package org.restframework.web.core.templates;

import org.restframework.web.annotations.Template;
import org.restframework.web.exceptions.ExceptionAdvice;

import java.util.List;
import java.util.Optional;

@Template(
        templateName="Controller",
        rule= SpringComponents.CONTROLLER,
        type=ClassTypes.CLASS
)
@SuppressWarnings("unused")
public interface TControllerCRUD<ID, DTO, Model> extends ExceptionAdvice {
    int insertEntity(DTO entity);
    List<DTO> getAllEntities();
    boolean removeEntityById(ID id);
    boolean updateEntity(ID id, Model entity);
    default Optional<Model> getById(ID id) { return Optional.empty(); }
    default Optional<Boolean> removeEntity(Model entityToRemove) { return Optional.empty(); }
    default Optional<Boolean> removeAllEntities() { return Optional.empty(); }
}
