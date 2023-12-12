package org.restframework.web.core.templates;

import org.restframework.web.annotations.Template;
import org.restframework.web.exceptions.ExceptionAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Template(
        templateName="Controller",
        rule= SpringComponents.CONTROLLER,
        generics={"UUID"},
        type=ClassTypes.CLASS
)
@SuppressWarnings("unused")
public interface ControllerTemplate<ID> extends ExceptionAdvice {

    int INSERT_NOT_IMPLEMENTED_CODE = 10000;
    boolean NOT_IMPLEMENTED = false;

    default int insertEntity(DtoFrame entity) { return INSERT_NOT_IMPLEMENTED_CODE; }
    default List<DtoFrame> getAllEntities() { return new ArrayList<>(); }
    default Optional<ModelFrame<ID>> getById(ID id) { return Optional.empty(); }
    default boolean removeEntity(ModelFrame<ID> entityToRemove) { return NOT_IMPLEMENTED; }
    default boolean removeEntityById(ID id) { return NOT_IMPLEMENTED; }
    default boolean removeAllEntities() { return NOT_IMPLEMENTED; }
    default boolean updateEntity(ID id, ModelFrame<ID> entity) { return NOT_IMPLEMENTED; }
}
