package org.restframework.web.annotations.gen;

import org.restframework.web.core.templates.TControllerCRUD;
import org.restframework.web.core.templates.TRepo;
import org.restframework.web.core.templates.TServiceCRUD;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@SuppressWarnings("unused")
public @interface GenSpring {
    Class<?> controller() default TControllerCRUD.class;
    Class<?> service() default TServiceCRUD.class;
    Class<?> repo() default TRepo.class;
}
