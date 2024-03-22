package org.restframework.web.annotations.gen;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Repeatable(value = GenComponents.class)
@SuppressWarnings("unused")
public @interface GenComponent {
    String name();
    String packageName() default "";
    String abbrev() default "Component";

}
