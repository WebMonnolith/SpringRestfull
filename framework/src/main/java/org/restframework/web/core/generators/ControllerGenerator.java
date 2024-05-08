package org.restframework.web.core.generators;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.restframework.web.WebApp;
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
                    .methods(defaultMethods() ? new _DefaultCrudControllerMethodImplementations() : WebApp.controllerMethods())
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

            TemplateResolver.TemplateID templateId = TemplateResolver.resolveTemplate(context.getTemplate());
            switch (templateId) {
                case TControllerCRUD -> {
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
                case TControllerEntityResponse -> {
                    context.getBuilder().addMethod(new MethodBuilder(
                            "insertEntity",
                            "ResponseEntity<Integer>",
                            Modifier.PUBLIC,
                            context.getDtoName() + " " + context.getDtoName().toLowerCase(),
                            "ResponseEntity.ok(" + serviceID+".insert("+context.getDtoName().toLowerCase()+"))",
                            new String[]{"Override", "PostMapping"}
                    ));

                    context.getBuilder().addMethod(new MethodBuilder(
                            "getAllEntities",
                            "ResponseEntity<List<"+context.getDtoName()+">>",
                            Modifier.PUBLIC, "",
                            "ResponseEntity.ok(" + serviceID+".getAll())",
                            new String[]{"Override", "GetMapping"}
                    ));

                    context.getBuilder().addMethod(new MethodBuilder(
                            "removeEntityById",
                            "ResponseEntity<Boolean>",
                            Modifier.PUBLIC,
                            context.getGeneric() + " id",
                            "ResponseEntity.ok(" + serviceID+".removeById(id))",
                            new String[]{"Override", "DeleteMapping"}
                    ));

                    context.getBuilder().addMethod(new MethodBuilder(
                            "updateEntity",
                            "ResponseEntity<Boolean>",
                            Modifier.PUBLIC,
                            context.getGeneric() + " id, " + context.getModelName() + " " + context.getModelName().toLowerCase(),
                            "ResponseEntity.ok(" + serviceID+".update(id, "+context.getModelName().toLowerCase()+"))",
                            new String[]{"Override", "PutMapping"}
                    ));
                }
                case TControllerEntityResponseWildcard -> {
                    context.getBuilder().addMethod(new MethodBuilder(
                            "insertEntity",
                            "ResponseEntity<?>",
                            Modifier.PUBLIC,
                            context.getDtoName() + " " + context.getDtoName().toLowerCase(),
                            "ResponseEntity.ok(" + serviceID+".insert("+context.getDtoName().toLowerCase()+"))",
                            new String[]{"Override", "PostMapping"}
                    ));

                    context.getBuilder().addMethod(new MethodBuilder(
                            "getAllEntities",
                            "ResponseEntity<?>",
                            Modifier.PUBLIC, "",
                            "ResponseEntity.ok(" + serviceID+".getAll())",
                            new String[]{"Override", "GetMapping"}
                    ));

                    context.getBuilder().addMethod(new MethodBuilder(
                            "removeEntityById",
                            "ResponseEntity<?>",
                            Modifier.PUBLIC,
                            context.getGeneric() + " id",
                            "ResponseEntity.ok(" + serviceID+".removeById(id))",
                            new String[]{"Override", "DeleteMapping"}
                    ));

                    context.getBuilder().addMethod(new MethodBuilder(
                            "updateEntity",
                            "ResponseEntity<?>",
                            Modifier.PUBLIC,
                            context.getGeneric() + " id, " + context.getModelName() + " " + context.getModelName().toLowerCase(),
                            "ResponseEntity.ok(" + serviceID+".update(id, "+context.getModelName().toLowerCase()+"))",
                            new String[]{"Override", "PutMapping"}
                    ));
                }
            }
        }
    }
}
