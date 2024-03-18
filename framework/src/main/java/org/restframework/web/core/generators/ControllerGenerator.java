package org.restframework.web.core.generators;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.restframework.web.annotations.types.API;
import org.restframework.web.annotations.Template;
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
public class ControllerGenerator extends Generator<Class<?>> {
    private final MvcSupport support;

    @Override
    public void generate(@NotNull API api, @NotNull Class<?> template, @NotNull String buildPath) {
        Template templateAnnotation = findTemplate(api, template);
        CompilationFlags.useModelsApi = false;
        GenericGeneration genericResolver = GenericFactory.create(api.model().generic());
        ClassBuilder mvcBuilder = buildComponent(api, templateAnnotation, this.support, genericResolver);

        this.compile(CompilationContext.builder()
                    .api(api)
                    .builder(mvcBuilder)
                    .template(template)
                    .templateAnnotation(templateAnnotation)
                    .modelName(api.model().apiName()+api.model().abbrev())
                    .dtoName(api.model().apiName()+"Dto")
                    .generic(genericResolver.getGeneric())
                    .defaultTemplateMethodImpl(defaultMethods())
                    .methods(new _DefaultCrudControllerMethodImplementations())
                    .build());

        mvcBuilder.build(buildPath, templateAnnotation.templateName().toLowerCase());
    }

    @Override
    protected void compile(@NotNull CompilationContext context) {
        CompilationProcessor.compile(context);
    }

    private static class _DefaultCrudControllerMethodImplementations implements MethodImplementations {
        @Override
        public void build(@NotNull CompilationContext context) {
            String serviceID = context.getApi().apiName().toLowerCase() + "Service";

            context.getBuilder().addMethod(new MethodBuilder(
                    "insertEntity",
                    "int",
                    Modifier.PUBLIC,
                    context.getDtoName() + " " + context.getDtoName().toLowerCase(),
                    serviceID+".insert("+context.getDtoName().toLowerCase()+")",
                    new String[]{"Override", "PostMapping", "ResponseStatus(HttpStatus.ACCEPTED)"}
            ));

            context.getBuilder().addMethod(new MethodBuilder(
                    "getAllEntities",
                    "List<"+context.getDtoName()+">",
                    Modifier.PUBLIC, "",
                    serviceID+".getAll()",
                    new String[]{"Override", "GetMapping", "ResponseStatus(HttpStatus.OK)"}
            ));

            context.getBuilder().addMethod(new MethodBuilder(
                    "removeEntityById",
                    "boolean",
                    Modifier.PUBLIC,
                    context.getGeneric() + " id",
                    serviceID+".removeById(id)",
                    new String[]{"Override", "DeleteMapping", "ResponseStatus(HttpStatus.FOUND)"}
            ));

            context.getBuilder().addMethod(new MethodBuilder(
                    "updateEntity",
                    "boolean",
                    Modifier.PUBLIC,
                    context.getGeneric() + " id, " + context.getModelName() + " " + context.getModelName().toLowerCase(),
                    serviceID+".update(id, "+context.getModelName().toLowerCase()+")",
                    new String[]{"Override", "PutMapping", "ResponseStatus(HttpStatus.OK)"}
            ));
        }
    }
}
