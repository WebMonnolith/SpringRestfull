package org.restframework.web.annotations.types;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@SuppressWarnings("unused")
public @interface API {
    String apiName();
    String endpoint();
    Model model();
    String apiPackage();
    String modelAbbrev() default "Model";
    String dtoAbbrev() default "Dto";
}
