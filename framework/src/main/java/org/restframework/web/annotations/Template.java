package org.restframework.web.annotations;

import org.restframework.web.core.templates.ClassTypes;
import org.restframework.web.core.templates.SpringComponents;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@SuppressWarnings("unused")
public @interface Template {
    String templateName();
    SpringComponents rule() default SpringComponents.NONE;
    ClassTypes type() default ClassTypes.CLASS;
}
