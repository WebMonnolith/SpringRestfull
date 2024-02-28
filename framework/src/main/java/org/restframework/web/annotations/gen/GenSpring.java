package org.restframework.web.annotations.gen;

import org.restframework.web.core.templates.ControllerTemplate;
import org.restframework.web.core.templates.RepoTemplate;
import org.restframework.web.core.templates.ServiceTemplate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@SuppressWarnings("unused")
public @interface GenSpring {
    Class<?>[] templates() default {
            ControllerTemplate.class,
            ServiceTemplate.class,
            RepoTemplate.class,
    };
}
