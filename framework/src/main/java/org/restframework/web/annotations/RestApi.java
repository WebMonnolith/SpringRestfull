package org.restframework.web.annotations;

import org.restframework.web.annotations.types.API;
import org.restframework.web.core.templates.ControllerTemplate;
import org.restframework.web.core.templates.RepoTemplate;
import org.restframework.web.core.templates.ServiceTemplate;
import org.springframework.stereotype.Component;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
@SuppressWarnings("unused")
@Component
public @interface RestApi {
    Class<?>[] templates() default {
        ControllerTemplate.class,
        ServiceTemplate.class,
        RepoTemplate.class,
    };
    API[] APIS();
}
