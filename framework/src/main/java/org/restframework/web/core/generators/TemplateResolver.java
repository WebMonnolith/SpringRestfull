package org.restframework.web.core.generators;

import org.jetbrains.annotations.NotNull;
import org.restframework.web.annotations.Template;
import org.restframework.web.exceptions.RestException;

public class TemplateResolver {
    public enum TemplateID {
        TControllerCRUD,
        TControllerEntityResponse,
        TControllerEntityResponseWildcard,
        TServiceCRUD,
        TServiceEntityResponse,
        TServiceEntityResponseWildcard,
    }

    public static TemplateID resolveTemplate(@NotNull Class<?> clazz) throws RestException {
        if (clazz.isAnnotationPresent(Template.class)) {
            Template templateAnnotation = clazz.getAnnotation(Template.class);
            String className = clazz.getSimpleName();

            switch (className) {
                case "TControllerCRUD" -> {
                    return TemplateID.TControllerCRUD;
                }
                case "TControllerEntityResponse" -> {
                    return TemplateID.TControllerEntityResponse;
                }
                case "TControllerEntityResponseWildcard" -> {
                    return TemplateID.TControllerEntityResponseWildcard;
                }
                case "TServiceCRUD" -> {
                    return TemplateID.TServiceCRUD;
                }
                case "TServiceEntityResponse" -> {
                    return TemplateID.TServiceEntityResponse;
                }
                case "TServiceEntityResponseWildcard" -> {
                    return TemplateID.TServiceEntityResponseWildcard;
                }
            }
        }
        throw new RestException("Has no @Template annotation");
    }
}
