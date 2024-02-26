package org.restframework.web.annotations;

import org.restframework.web.core.generics.Generic;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@SuppressWarnings("unused")
public @interface Model {
    Generic generic();
    String tableName();
    String apiName();
    String abbrev() default "Model";
    FieldData[] fields() default {};
}
