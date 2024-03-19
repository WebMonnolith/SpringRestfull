package org.restframework.web.core.generators.compilation;

import org.jetbrains.annotations.NotNull;
import org.restframework.web.annotations.types.FieldData;
import org.restframework.web.core.templates.SpringComponents;

import static org.restframework.web.core.helpers.ModelHelper.convertToFieldBuilder;
import static org.restframework.web.core.templates.TemplateUtils.*;

public class CompilationProcessor {

    public static void compile(@NotNull CompilationContext context) {
        if (!CompilationFlags.useModelsApi) {
            CompilationStrategy strategy = CompilationStrategyFactory.createStrategy(context);
            checkInheritance(context);
            strategy.execute(context);
            CompilationFlags.customRepoGenerics = false;
        } else
            handleModelsApi(context);
    }

    private static void handleModelsApi(@NotNull CompilationContext context) {
        for (FieldData data : context.getModelAnnotation().fields())
            context.getBuilder().addField(convertToFieldBuilder(data).addStatement("\n"));
    }

    private static void checkInheritance(@NotNull CompilationContext context) {
        if (!context.getModelName().isEmpty())
            CompilationFlags.customRepoGenerics = true;

        if (CompilationFlags.customRepoGenerics && useImplementation(context.getTemplateAnnotation()))
            if (context.getTemplateAnnotation().rule() == SpringComponents.REPO)
                context.getBuilder().addInterface(context.getTemplate(),
                         context.getModelName(),
                        context.getGeneric());
            else
                context.getBuilder().addInterface(context.getTemplate(),
                        context.getGeneric(),
                            context.getDtoName(),
                            context.getModelName()
                        );
        else if (CompilationFlags.customRepoGenerics && useInheritance(context.getTemplateAnnotation()))
            context.getBuilder().addExtension(context.getTemplate(),
                    context.getModelName(),
                    context.getGeneric());

//        if (useImplementation(context.getTemplateAnnotation()) && ! CompilationFlags.customRepoGenerics)
//            if (hasGenerics(context.getTemplateAnnotation()))
//                context.getBuilder().addInterface(context.getTemplate());
//            else
//                context.getBuilder().addInterface(context.getTemplate(), context.getTemplateAnnotation().generics());
//        else if (useInheritance(context.getTemplateAnnotation()) && ! CompilationFlags.customRepoGenerics)
//            if (hasGenerics(context.getTemplateAnnotation()))
//                context.getBuilder().addExtension(context.getTemplate());
//            else
//                context.getBuilder().addExtension(context.getTemplate(), context.getTemplateAnnotation().generics());
    }
}
