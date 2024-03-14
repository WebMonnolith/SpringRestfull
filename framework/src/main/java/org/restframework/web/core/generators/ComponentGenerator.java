package org.restframework.web.core.generators;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.restframework.web.annotations.types.API;
import org.restframework.web.annotations.RestApi;
import org.restframework.web.annotations.Template;
import org.restframework.web.core.builders.ClassBuilder;
import org.restframework.web.core.generators.compilation.CompilationContext;
import org.restframework.web.core.generators.compilation.CompilationFlags;
import org.restframework.web.core.generators.compilation.CompilationProcessor;
import org.restframework.web.core.generics.GenericFactory;
import org.restframework.web.core.generics.GenericGeneration;
import org.restframework.web.exceptions.RestException;
import org.springframework.core.annotation.AnnotationUtils;

import static org.restframework.web.WebApp.defaultMethods;

@AllArgsConstructor
public class ComponentGenerator extends Generator<Class<?>> {
    private final MvcSupport support;

    @Override
    public void generate(@NotNull API api, @NotNull Class<?> template, @NotNull String buildPath) {
        Template templateAnnotation = AnnotationUtils.findAnnotation(template, Template.class);
        if (templateAnnotation == null)
            throw new RestException("@" + RestApi.class + "templates must be annotated with @Template " +
                    api.basePackage() + "\n" + api.apiName());
        CompilationFlags.useModelsApi = false;

        GenericGeneration genericResolver = GenericFactory.create(api.model().generic());
        ClassBuilder mvcBuilder = new ClassBuilder(
                api.apiName()+templateAnnotation.templateName(),
                api.basePackage()+'.'+templateAnnotation.templateName().toLowerCase(),
                this.support.callInner(templateAnnotation.rule(), api.endpoint()),
                templateAnnotation.type(),
                new ImportResolver(templateAnnotation.rule(), genericResolver.getImportStatement(), api.basePackage()).get());

        this.compile(CompilationContext.builder()
                    .api(api)
                    .builder(mvcBuilder)
                    .template(template)
                    .templateAnnotation(templateAnnotation)
                    .modelName(api.model().apiName()+api.model().abbrev())
                    .dtoName(api.model().apiName()+"Dto")
                    .generic(genericResolver.getGeneric())
                    .defaultTemplateMethodImpl(defaultMethods())
                    .build());

        mvcBuilder.build(buildPath, templateAnnotation.templateName().toLowerCase());
    }

    @Override
    protected void compile(@NotNull CompilationContext context) {
        CompilationProcessor.compile(context);
    }
}
