package org.restframework.web.core.templates;

import org.restframework.web.annotations.Template;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Template(
        templateName="Service",
        rule= SpringComponents.SERVICE,
        generics={"UUID"},
        type=ClassTypes.CLASS
)
@SuppressWarnings("unused")
public interface ServiceTemplate<ID> {

    int INSERT_NOT_IMPLEMENTED_CODE = 10000;
    boolean NOT_IMPLEMENTED = false;

    default int insert(DtoFrame dto) { return INSERT_NOT_IMPLEMENTED_CODE; }
    default boolean update(ID id, ModelFrame<ID> entity) {
        return NOT_IMPLEMENTED;
    }
    default boolean remove(ModelFrame<ID> entity) {
        return NOT_IMPLEMENTED;
    }
    default boolean removeById(ID id) {
        return NOT_IMPLEMENTED;
    }
    default <Repo extends RepoTemplate<ModelFrame<ID>, ID>> boolean removeById(@NotNull ID id, @NotNull Repo repository) {
        boolean isAvailable = this.exists(id, repository);
        if (isAvailable)
            repository.deleteById(id);
        return isAvailable;
    }
    default List<DtoFrame> getAll() { return new ArrayList<>(); }
    default Optional<ModelFrame<ID>> getById(ID id) { return Optional.empty(); }
    default <Repo extends RepoTemplate<ModelFrame<ID>, ID>> boolean exists(@NotNull ID id, @NotNull Repo repository) {
        List<ModelFrame<ID>> entities = repository.findAll();
        boolean isAvailable = false;
        for (ModelFrame<ID> entity : entities)
            if (entity.getId().equals(id)) {
                isAvailable = true;
                break;
            }
        return isAvailable;
    }
    default boolean removeAll() {
        return NOT_IMPLEMENTED;
    }
    default <Repo extends RepoTemplate<ModelFrame<ID>, ID>> boolean removeAll(@NotNull Repo repository) {
        repository.deleteAll();
        return true;
    }
}
