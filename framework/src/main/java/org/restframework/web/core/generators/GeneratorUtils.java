package org.restframework.web.core.generators;

import org.jetbrains.annotations.NotNull;
import org.restframework.web.WebApp;
import org.restframework.web.annotations.RestApi;
import org.restframework.web.annotations.Template;
import org.restframework.web.annotations.types.API;
import org.restframework.web.core.builders.ClassBuilder;
import org.restframework.web.core.generics.GenericGeneration;
import org.restframework.web.exceptions.RestException;
import org.springframework.core.annotation.AnnotationUtils;

public class GeneratorUtils {
    public static @NotNull Template findTemplate(@NotNull API api, @NotNull Class<?> template) {
        Template templateAnnotation = AnnotationUtils.findAnnotation(template, Template.class);
        if (templateAnnotation == null)
            throw new RestException("@" + RestApi.class.getSimpleName() + "templates must be annotated with @Template " +
                    api.apiPackage() + "." + api.apiName());

        return templateAnnotation;
    }

    public static ClassBuilder buildComponent(
            @NotNull API api,
            @NotNull Template templateAnnotation,
            @NotNull MvcSupport support,
            @NotNull GenericGeneration resolver
    ) {
        String packageName = "";
        switch (WebApp.strategy()) {
            case WEB_REST_API_STRATEGY -> packageName = String.format("%s.%s", WebApp.context().basePackage(), api.apiPackage());
            case WEB_CUSTOM_GENERATION_STRATEGY ->  packageName = api.apiPackage();
        }
        return new ClassBuilder(
                api.apiName()+templateAnnotation.templateName(),
                packageName+'.'+templateAnnotation.templateName().toLowerCase(),
                support.callInner(templateAnnotation.rule(), api.endpoint()),
                templateAnnotation.type(),
                new ImportResolver(templateAnnotation.rule(), resolver.getImportStatement(), packageName).get());
    }

}
