package org.restframework.web.core.templates;

import org.restframework.web.annotations.Template;

import java.util.List;
import java.util.Optional;

@Template(
        templateName="Service",
        rule= SpringComponents.SERVICE,
        type=ClassTypes.CLASS
)
@SuppressWarnings("unused")
public interface TServiceBasic {
    ServiceTemplateUtils utils = new ServiceTemplateUtils();
    <T> int insert(T dto);
    <T> List<?> getAll();
    <ID> boolean removeById(ID id);
    <ID, T> boolean update(ID id, T entity);
    default <T> Optional<Boolean> remove(T entity) {
        return Optional.empty();
    }
    default <ID, T> Optional<T> getById(ID id) { return Optional.empty(); }
    default Optional<Boolean> removeAll() {
        return Optional.empty();
    }
}
