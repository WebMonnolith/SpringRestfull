package org.restframework.web.core.templates;

import org.restframework.web.annotations.Template;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

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
public interface ServiceTemplate<ID, DTO, Model extends ModelFrame<ID>> {

    int INSERT_NOT_IMPLEMENTED_CODE = 10000;
    boolean NOT_IMPLEMENTED = false;

    default int insert(DTO dto) { return INSERT_NOT_IMPLEMENTED_CODE; }
    default boolean update(ID id, Model entity) {
        return NOT_IMPLEMENTED;
    }
    default boolean remove(Model entity) {
        return NOT_IMPLEMENTED;
    }
    default boolean removeById(ID id) {
        return NOT_IMPLEMENTED;
    }
    default <Repo extends JpaRepository<Model, ID>>  boolean removeById(@NotNull ID id, @NotNull Repo repository) {
        boolean isAvailable = this.exists(id, repository);
        if (isAvailable)
            repository.deleteById(id);
        return isAvailable;
    }
    default List<DTO> getAll() { return new ArrayList<>(); }
    default Optional<Model> getById(ID id) { return Optional.empty(); }
    default <Repo extends JpaRepository<Model, ID>> boolean exists(@NotNull ID id, @NotNull Repo repository) {
        return repository.existsById(id);
    }
    default <Repo extends JpaRepository<Model, ID>> boolean existsUsingFindAll(@NotNull ID id, @NotNull Repo repository) {
        List<Model> entities = repository.findAll();
        boolean isAvailable = false;
        for (Model entity : entities)
            if (entity.getId().equals(id)) {
                isAvailable = true;
                break;
            }
        return isAvailable;
    }
    default boolean removeAll() {
        return NOT_IMPLEMENTED;
    }
    default <Repo extends JpaRepository<Model, ID>> boolean removeAll(@NotNull Repo repository) {
        repository.deleteAll();
        return true;
    }
}
