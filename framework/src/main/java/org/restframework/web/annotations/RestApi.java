package org.restframework.web.annotations;

import org.restframework.web.annotations.types.API;
import org.restframework.web.core.templates.TControllerCRUD;
import org.restframework.web.core.templates.TRepo;
import org.restframework.web.core.templates.TServiceCRUD;
import org.springframework.stereotype.Component;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
@SuppressWarnings("unused")
@Component
public @interface RestApi {
    Class<?> controller() default TControllerCRUD.class;
    Class<?> service() default TServiceCRUD.class;
    Class<?> repo() default TRepo.class;
    String basePackage();
    API[] APIS();
}
