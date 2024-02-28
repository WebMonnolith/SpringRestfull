package org.restframework.web.annotations.gen;

import org.restframework.web.core.templates.ControllerTemplate;
import org.restframework.web.core.templates.RepoTemplate;
import org.restframework.web.core.templates.ServiceTemplate;

public @interface GenSpring {
    Class<?>[] templates() default {
            ControllerTemplate.class,
            ServiceTemplate.class,
            RepoTemplate.class,
    };
}
