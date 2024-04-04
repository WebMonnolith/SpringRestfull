package org.restframework.web.annotations.gen;

import org.restframework.web.core.templates.TServiceBasic;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Repeatable(value = GenServices.class)
@SuppressWarnings("unused")
public @interface GenService {
    String name();
    Class<?> template() default TServiceBasic.class;
    String abbrev() default "Service";
}
