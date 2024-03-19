package org.restframework.web.core.templates;

import org.restframework.web.annotations.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Template(
        templateName="Repository",
        rule= SpringComponents.REPO,
        type=ClassTypes.INTERFACE
)
@SuppressWarnings("unused")
@Repository
public interface TRepo<T extends ModelFrame<ID>, ID> extends JpaRepository<T, ID> {
}
