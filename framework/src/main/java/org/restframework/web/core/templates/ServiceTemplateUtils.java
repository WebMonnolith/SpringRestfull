package org.restframework.web.core.templates;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@SuppressWarnings("unused")
public class ServiceTemplateUtils {

    public ServiceTemplateUtils() {}

    public <ID, Model extends ModelFrame<ID>, Repo extends JpaRepository<Model, ID>> boolean exists(@NotNull ID id, @NotNull Repo repository) {
        return repository.existsById(id);
    }

    public <ID, Model extends ModelFrame<ID>, Repo extends JpaRepository<Model, ID>> boolean existsUsingFindAll(@NotNull ID id, @NotNull Repo repository) {
        List<Model> entities = repository.findAll();
        boolean isAvailable = false;
        for (Model entity : entities)
            if (entity.getId().equals(id)) {
                isAvailable = true;
                break;
            }
        return isAvailable;
    }

    public <ID, Model extends ModelFrame<ID>, Repo extends JpaRepository<Model, ID>>  boolean removeById(@NotNull ID id, @NotNull Repo repository) {
        boolean isAvailable = this.exists(id, repository);
        if (isAvailable)
            repository.deleteById(id);
        return isAvailable;
    }

    public <ID, Model extends ModelFrame<ID>, Repo extends JpaRepository<Model, ID>> boolean removeAll(@NotNull Repo repository) {
        repository.deleteAll();
        return true;
    }
}
