package org.restframework.web.core.generators;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.restframework.web.WebApp;
import org.restframework.web.annotations.FieldData;
import org.restframework.web.annotations.Model;
import org.restframework.web.annotations.RestApi;
import org.restframework.web.annotations.Template;
import org.restframework.web.core.Modifier;
import org.restframework.web.core.generators.builders.ClassBuilder;
import org.restframework.web.core.generators.builders.FieldBuilder;
import org.restframework.web.core.templates.ClassTypes;
import org.restframework.web.core.templates.ModelFrame;
import org.restframework.web.core.templates.SpringComponents;
import org.restframework.web.exceptions.RestException;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.Arrays;

import static org.restframework.web.core.FileHelper.NO_DIR;
import static org.restframework.web.core.ModelHelper.convertToFieldBuilder;
import static org.restframework.web.core.templates.TemplateUtils.*;

@Slf4j
public final class MvcGenerator {

    @Data
    @AllArgsConstructor
    @Builder
    private static class CompilationContext {
        private RestApi restApi;
        private ClassBuilder builder;
        private Class<?> template;
        private Template templateAnnotation;
        private Model modelAnnotation;
        private String modelName;
        private int index;

    }

    private static MvcSupport support;

    public static class CompilationFlags {
        public static boolean generateModelsOnce = false;
        public static boolean useModelsApi = false;
        public static boolean customRepoGenerics = false;

    }

    public synchronized static void generateClasses(
            @NotNull RestApi restApi,
            @NotNull Class<?> template,
            @NotNull MvcSupport support)
    {
        Template templateAnnotation = AnnotationUtils.findAnnotation(template, Template.class);
        if (templateAnnotation == null)
            throw new RestException("@" + RestApi.class + "templates must be annotated with @Template " +
                    restApi.basePackage() + "\n" + Arrays.toString(restApi.apiNames()));

        MvcGenerator.support = support;
        CompilationFlags.useModelsApi = false;

        for (int i = 0; i < restApi.apiNames().length; i++) {
            ClassBuilder mvcBuilder = new ClassBuilder(
                    restApi.apiNames()[i]+templateAnnotation.templateName(),
                    restApi.basePackage()+'.'+templateAnnotation.templateName().toLowerCase(),
                    MvcGenerator.support.callInner(templateAnnotation.rule(), restApi.endpoints()[i]),
                    templateAnnotation.type());

            MvcGenerator.compile(CompilationContext.builder()
                    .restApi(restApi)
                    .builder(mvcBuilder)
                    .template(template)
                    .templateAnnotation(templateAnnotation)
                    .modelName(restApi.models()[i].apiName()+restApi.models()[i].abbrev())
                    .index(i)
                    .build());

            mvcBuilder.build(WebApp.outputResultPathBase(), templateAnnotation.templateName().toLowerCase());
        }

    }

    public synchronized static void generateModels(
            @NotNull RestApi restApi,
            boolean flag) {

        if (CompilationFlags.generateModelsOnce)
            return;

        CompilationFlags.useModelsApi = true;
        Model[] models = restApi.models();

        if (models.length == 0)
            return;

        for (int i = 0; i < models.length; i++) {
            if (models[i].apiName().equals(restApi.apiNames()[i])) {
                ClassBuilder modelBuilder = new ClassBuilder(
                        models[i].apiName()+models[i].abbrev(),
                        restApi.basePackage(),
                        MvcGenerator.support.callInner(SpringComponents.MODEL, models[i].tableName()),
                        ClassTypes.CLASS);

                modelBuilder.addExtension(ModelFrame.class, models[i].generic());

                MvcSupport support = new MvcSupport() {
                    @Override
                    public void call(SpringComponents rules, String value) {
                        ruleHolder.add(PersistenceAnnotations.ID.getValue());
                        ruleHolder.add(this.makeGenerationType(value));
                    }
                };

                modelBuilder.addField(
                        new FieldBuilder(
                                "id",
                                models[i].generic(),
                                Modifier.PRIVATE,
                                support.callInner(SpringComponents.MODEL, models[i].generic())));

                MvcGenerator.compile(CompilationContext.builder()
                        .restApi(restApi)
                        .builder(modelBuilder)
                        .modelAnnotation(models[i])
                        .index(i)
                        .build()
                );
                modelBuilder.build(WebApp.outputResultPathBase(), NO_DIR);
            }
        }

        if (flag) {
            CompilationFlags.generateModelsOnce = true;
        }}

    private static void compile(@NotNull CompilationContext context) {
        if (!CompilationFlags.useModelsApi) {
            if (!context.getModelName().isEmpty() && context.getTemplateAnnotation().rule() == SpringComponents.REPO)
                CompilationFlags.customRepoGenerics = true;

            if (CompilationFlags.customRepoGenerics && useImplementation(context.getTemplateAnnotation()))
                if (hasGenerics(context.getTemplateAnnotation()))
                    context.getBuilder().addInterface(context.getTemplate());
                else
                    context.getBuilder().addInterface(context.getTemplate(),
                            context.getRestApi().basePackage() + '.' + context.getModelName(),
                            "UUID");
            else if (CompilationFlags.customRepoGenerics && useInheritance(context.getTemplateAnnotation()))
                if (hasGenerics(context.getTemplateAnnotation()))
                    context.getBuilder().addExtension(context.getTemplate());
                else
                    context.getBuilder().addExtension(context.getTemplate(),
                            context.getRestApi().basePackage() + '.' +  context.getModelName(),
                            "UUID");

            if (useImplementation(context.getTemplateAnnotation()) && ! CompilationFlags.customRepoGenerics)
                if (hasGenerics(context.getTemplateAnnotation()))
                    context.getBuilder().addInterface(context.getTemplate());
                else
                    context.getBuilder().addInterface(context.getTemplate(), context.getTemplateAnnotation().generics());
            else if (useInheritance(context.getTemplateAnnotation()) && ! CompilationFlags.customRepoGenerics)
                if (hasGenerics(context.getTemplateAnnotation()))
                    context.getBuilder().addExtension(context.getTemplate());
                else
                    context.getBuilder().addExtension(context.getTemplate(), context.getTemplateAnnotation().generics());

            if (context.getTemplateAnnotation().rule() == SpringComponents.SERVICE)
                context.getBuilder().addField(
                        new FieldBuilder(
                                "repository",
                                context.getRestApi().basePackage() + ".repository." +
                                        context.getRestApi().apiNames()[context.getIndex()] + "Repository",
                                Modifier.PRIVATE_FINAL));

            if (context.getTemplateAnnotation().rule() == SpringComponents.CONTROLLER)
                context.getBuilder().addField(
                        new FieldBuilder(
                                "service" + context.getRestApi().apiNames()[context.getIndex()],
                                context.getRestApi().basePackage() + ".service." +
                                        context.getRestApi().apiNames()[context.getIndex()] + "Service",
                                Modifier.PRIVATE_FINAL));

            CompilationFlags.customRepoGenerics = false;
        }
        else
            for (FieldData data : context.getModelAnnotation().fields())
                context.getBuilder().addField(convertToFieldBuilder(data).addStatement("\n"));
    }
}
