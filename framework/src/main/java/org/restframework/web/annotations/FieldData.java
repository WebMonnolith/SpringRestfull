package org.restframework.web.annotations;

import org.restframework.web.core.builders.Modifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@SuppressWarnings("unused")
public @interface FieldData {
    Modifier access() default Modifier.PRIVATE;
    String datatype() default "String";
    String name();

}
