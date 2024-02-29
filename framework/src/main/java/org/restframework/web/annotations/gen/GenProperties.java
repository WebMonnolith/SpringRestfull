package org.restframework.web.annotations.gen;

import org.restframework.web.core.generics.Generic;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@SuppressWarnings("unused")
public @interface GenProperties {
    Generic indexColumnType() default Generic.UUID;
    String apiName() default "Api";
    String endpoint() default "api/v1/endpoint";
    String basePackage() default "";
}
