package org.restframework.web.core.generators;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.restframework.web.annotations.*;
import org.restframework.web.core.builders.Modifier;
import org.restframework.web.core.builders.ClassBuilder;
import org.restframework.web.core.builders.FieldBuilder;
import org.restframework.web.core.generators.compilation.CompilationContext;
import org.restframework.web.core.generators.compilation.CompilationFlags;
import org.restframework.web.core.generators.compilation.CompilationProcessor;
import org.restframework.web.core.templates.ClassTypes;
import org.restframework.web.core.templates.DtoFrame;
import org.restframework.web.core.templates.ModelFrame;
import org.restframework.web.core.templates.SpringComponents;
import org.restframework.web.exceptions.RestException;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.annotation.AnnotationUtils;

import static org.restframework.web.core.helpers.FileHelper.NO_DIR;


@Slf4j
public final class MvcGenerator {

    private static MvcSupport support;

    public synchronized static void generateClasses(
            @NotNull API api,
            @NotNull Class<?> template,

            @NotNull String buildPath)
    {
        Template templateAnnotation = AnnotationUtils.findAnnotation(template, Template.class);
        if (templateAnnotation == null)
            throw new RestException("@" + RestApi.class + "templates must be annotated with @Template " +
                    api.basePackage() + "\n" + api.apiName());

        CompilationFlags.useModelsApi = false;

        ClassBuilder mvcBuilder = new ClassBuilder(
                api.apiName()+templateAnnotation.templateName(),
                api.basePackage()+'.'+templateAnnotation.templateName().toLowerCase(),
                this.support.callInner(templateAnnotation.rule(), api.endpoint()),
                templateAnnotation.type(),
                new ImportResolver(templateAnnotation.rule(), api.basePackage()).get());

        MvcGenerator.compile(CompilationContext.builder()
                .api(api)
                .builder(mvcBuilder)
                .template(template)
                .templateAnnotation(templateAnnotation)
                .modelName(api.model().apiName()+api.model().abbrev())
                .dtoName(api.model().apiName()+"Dto")
                .build());

        mvcBuilder.build(buildPath, templateAnnotation.templateName().toLowerCase());
    }

    public synchronized void generateByKey(
            @NotNull API api,
            boolean flag,
            @NotNull SpringComponents component,
            @NotNull String buildPath) {

        if (CompilationFlags.generateModelsOnce)
            return;

        CompilationFlags.useModelsApi = true;

        String value = "";
        String name = api.model().apiName()+"Dto";
        if (component == SpringComponents.MODEL) {
            value = api.model().tableName();
            name = api.model().apiName()+api.model().abbrev();
        }

        ClassBuilder modelBuilder = new ClassBuilder(
                name,
                api.basePackage(),
                this.support.callInner(component, value),
                ClassTypes.CLASS,
                new ImportResolver(component, api.basePackage()).get());

        if (component == SpringComponents.MODEL) {
            modelBuilder.addExtension(ModelFrame.class, api.model().generic());

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
                            api.model().generic(),
                            Modifier.PRIVATE,
                            support.callInner(SpringComponents.MODEL, api.model().generic())));
        }
        else
            modelBuilder.addExtension(DtoFrame.class);

        MvcGenerator.compile(CompilationContext.builder()
                .api(api)
                .builder(modelBuilder)
                .modelAnnotation(api.model())
                .dtoName(api.model().apiName()+"Dto")
                .build()
        );

        modelBuilder.build(buildPath, NO_DIR);


        if (flag) {
            CompilationFlags.generateModelsOnce = true;
        }
    }

    private static void compile(@NotNull CompilationContext context) {
        CompilationProcessor.compile(context);
    }


//        if (!CompilationFlags.useModelsApi) {
//            if (!context.getModelName().isEmpty())
//                CompilationFlags.customRepoGenerics = true;
//
//            if (CompilationFlags.customRepoGenerics && useImplementation(context.getTemplateAnnotation()))
//                if (hasGenerics(context.getTemplateAnnotation()))
//                    context.getBuilder().addInterface(context.getTemplate());
//                else
//                    if (context.getTemplateAnnotation().rule() == SpringComponents.REPO)
//                        context.getBuilder().addInterface(context.getTemplate(),
//                                 context.getModelName(),
//                                "UUID");
//                    else
//                        context.getBuilder().addInterface(context.getTemplate(),
//                                "UUID",
//                                    context.getDtoName(),
//                                    context.getModelName()
//                                );
//            else if (CompilationFlags.customRepoGenerics && useInheritance(context.getTemplateAnnotation()))
//                if (hasGenerics(context.getTemplateAnnotation()))
//                    context.getBuilder().addExtension(context.getTemplate());
//                else
//                    context.getBuilder().addExtension(context.getTemplate(),
//                            context.getApi().basePackage() + '.' +  context.getModelName(),
//                            "UUID");
//
//            if (useImplementation(context.getTemplateAnnotation()) && ! CompilationFlags.customRepoGenerics)
//                if (hasGenerics(context.getTemplateAnnotation()))
//                    context.getBuilder().addInterface(context.getTemplate());
//                else
//                    context.getBuilder().addInterface(context.getTemplate(), context.getTemplateAnnotation().generics());
//            else if (useInheritance(context.getTemplateAnnotation()) && ! CompilationFlags.customRepoGenerics)
//                if (hasGenerics(context.getTemplateAnnotation()))
//                    context.getBuilder().addExtension(context.getTemplate());
//                else
//                    context.getBuilder().addExtension(context.getTemplate(), context.getTemplateAnnotation().generics());
//
//            if (context.getTemplateAnnotation().rule() == SpringComponents.SERVICE)
//                context.getBuilder().addField(
//                        new FieldBuilder(
//                                "repository",
//                                context.getApi().basePackage() + ".repository." +
//                                        context.getApi().apiName() + "Repository",
//                                Modifier.PRIVATE_FINAL));
//
//            if (context.getTemplateAnnotation().rule() == SpringComponents.CONTROLLER)
//                context.getBuilder().addField(
//                        new FieldBuilder(
//                                "service" + context.getApi().apiName(),
//                                context.getApi().basePackage() + ".service." +
//                                        context.getApi().apiName() + "Service",
//                                Modifier.PRIVATE_FINAL));
//
//            CompilationFlags.customRepoGenerics = false;
//        }
    //        else
//            for (FieldData data : context.getModelAnnotation().fields())
//                context.getBuilder().addField(convertToFieldBuilder(data).addStatement("\n"))
}


