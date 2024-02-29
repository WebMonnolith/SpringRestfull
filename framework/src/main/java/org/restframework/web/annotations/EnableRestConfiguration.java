package org.restframework.web.annotations;

import org.restframework.web.core.templates.SpringComponents;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@SuppressWarnings("unused")
public @interface EnableRestConfiguration {
    String contentRoot() default "/src/main/java";
    boolean useCustomGenerationStrategy() default false;
    SpringComponents modelComponent() default SpringComponents.MODEL;
    SpringComponents dtoComponent() default SpringComponents.DTO;
}
