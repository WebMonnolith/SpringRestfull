package org.restframework.web.core.templates;

import org.restframework.web.annotations.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Template(
        templateName="Repository",
        rule= SpringComponents.REPO,
        generics={"org.framework.web.core.templates.ModelFrame<UUID>", "UUID"},
        type=ClassTypes.INTERFACE
)
@SuppressWarnings("unused")
@Repository
public interface RepoTemplate <T extends ModelFrame<ID>, ID> extends JpaRepository<T, ID> {
}
