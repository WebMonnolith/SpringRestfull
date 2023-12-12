package org.restframework.web.annotations;

import org.springframework.stereotype.Component;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
@SuppressWarnings("unused")
@Component
public @interface RestApi {
    String basePackage();
    Class<?>[] templates();
    String[] apiNames() default {};
    String[] endpoints() default {};
    Model[] models() default {};
}
