package org.restframework.web.core.generators.compilation;

import org.jetbrains.annotations.NotNull;
import org.restframework.web.annotations.FieldData;

import static org.restframework.web.core.helpers.ModelHelper.convertToFieldBuilder;

public class CompilationProcessor {

    public static void compile(@NotNull CompilationContext context) {
        if (!CompilationFlags.useModelsApi) {
            CompilationStrategy strategy = CompilationStrategyFactory.createStrategy(context);
            strategy.execute(context);
        } else
            handleModelsApi(context);
    }

    private static void handleModelsApi(@NotNull CompilationContext context) {
        for (FieldData data : context.getModelAnnotation().fields())
            context.getBuilder().addField(convertToFieldBuilder(data).addStatement("\n"));
    }
}
