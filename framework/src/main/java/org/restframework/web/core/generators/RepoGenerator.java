package org.restframework.web.core.generators;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.restframework.web.annotations.Template;
import org.restframework.web.annotations.types.API;
import org.restframework.web.core.builders.ClassBuilder;
import org.restframework.web.core.generators.compilation.CompilationContext;
import org.restframework.web.core.generators.compilation.CompilationFlags;
import org.restframework.web.core.generators.compilation.CompilationProcessor;
import org.restframework.web.core.generics.GenericFactory;
import org.restframework.web.core.generics.GenericGeneration;

import static org.restframework.web.WebApp.defaultMethods;
import static org.restframework.web.core.generators.GeneratorUtils.buildComponent;
import static org.restframework.web.core.generators.GeneratorUtils.findTemplate;

@RequiredArgsConstructor
public class RepoGenerator extends Generator<Class<?>> {

    private final MvcSupport support;

    @Override
    public void generate(
            @NotNull API api,
            @NotNull Class<?> template,
            @NotNull String buildPath
    ) {
        Template templateAnnotation = findTemplate(api, template);
        CompilationFlags.useModelsApi = false;
        GenericGeneration genericResolver = GenericFactory.create(api.model().generic());
        ClassBuilder mvcBuilder = buildComponent(api, templateAnnotation, this.support, genericResolver);

        this.compile(CompilationContext.builder()
                .api(api)
                .builder(mvcBuilder)
                .template(template)
                .templateAnnotation(templateAnnotation)
                .modelName(api.apiName()+api.model().abbrev())
                .dtoName(api.apiName()+"Dto")
                .generic(genericResolver.getGeneric())
                .build());

        mvcBuilder.build(buildPath, templateAnnotation.templateName().toLowerCase());
    }

    @Override
    protected void compile(@NotNull CompilationContext context) {
        CompilationProcessor.compile(context);
    }
}
