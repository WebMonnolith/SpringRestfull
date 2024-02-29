package org.restframework.web.annotations.gen;

import org.restframework.web.annotations.types.FieldData;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@SuppressWarnings("unused")
public @interface GenModel {
    String tableName();
    FieldData[] fields() default {};
}
