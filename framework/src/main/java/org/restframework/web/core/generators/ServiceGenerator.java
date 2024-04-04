package org.restframework.web.core.generators;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.restframework.web.WebApp;
import org.restframework.web.annotations.Template;
import org.restframework.web.annotations.types.API;
import org.restframework.web.core.builders.ClassBuilder;
import org.restframework.web.core.builders.MethodBuilder;
import org.restframework.web.core.builders.Modifier;
import org.restframework.web.core.generators.compilation.CompilationContext;
import org.restframework.web.core.generators.compilation.CompilationFlags;
import org.restframework.web.core.generators.compilation.CompilationProcessor;
import org.restframework.web.core.generators.compilation.MethodImplementations;
import org.restframework.web.core.generics.GenericFactory;
import org.restframework.web.core.generics.GenericGeneration;

import static org.restframework.web.WebApp.defaultMethods;
import static org.restframework.web.core.generators.GeneratorUtils.buildComponent;
import static org.restframework.web.core.generators.GeneratorUtils.findTemplate;

@RequiredArgsConstructor
public class ServiceGenerator extends Generator<Class<?>> {

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
                .modelName(api.apiName()+api.modelAbbrev())
                .dtoName(api.apiName()+api.dtoAbbrev())
                .generic(genericResolver.getGeneric())
                .componentType(templateAnnotation.rule())
                .methods(defaultMethods() ? new _DefaultCrudServiceMethodImplementations() : WebApp.controllerMethods())
                .build());

        mvcBuilder.build(buildPath, templateAnnotation.templateName().toLowerCase());
    }

    @Override
    protected void compile(@NotNull CompilationContext context) {
        CompilationProcessor.compile(context);
    }

    private static class _DefaultCrudServiceMethodImplementations implements MethodImplementations {
        @Override
        public void build(@NotNull CompilationContext context) {
            context.getBuilder().addMethod(new MethodBuilder(
                    "insert",
                    "int",
                    Modifier.PUBLIC,
                    context.getDtoName() + " " + context.getDtoName().toLowerCase(),
                    "0",
                    new String[]{"Override"}
            ));

            context.getBuilder().addMethod(new MethodBuilder(
                    "getAll",
                    "List<"+context.getDtoName()+">",
                    Modifier.PUBLIC,
                    new String[]{"Override"}
            ));

            context.getBuilder().addMethod(new MethodBuilder(
                    "removeById",
                    "boolean",
                    Modifier.PUBLIC,
                    context.getGeneric() + " id",
                    "false",
                    new String[]{"Override"}
            ));

            context.getBuilder().addMethod(new MethodBuilder(
                    "update",
                    "boolean",
                    Modifier.PUBLIC,
                    context.getGeneric() + " id, " + context.getModelName() + " " + context.getModelName().toLowerCase(),
                    "false",
                    new String[]{"Override"}
            ));
        }
    }

}
