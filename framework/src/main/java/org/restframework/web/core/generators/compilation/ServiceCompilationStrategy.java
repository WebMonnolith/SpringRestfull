package org.restframework.web.core.generators.compilation;

import org.jetbrains.annotations.NotNull;
import org.restframework.web.core.builders.FieldBuilder;
import org.restframework.web.core.builders.MethodBuilder;
import org.restframework.web.core.builders.Modifier;

public class ServiceCompilationStrategy implements CompilationStrategy {

    @Override
    public void execute(@NotNull CompilationContext context) {
        context.getBuilder().addField(
                new FieldBuilder(
                        "repository",
                        context.getApi().apiName() + "Repository",
                        Modifier.PRIVATE_FINAL));

        if (context.isDefaultTemplateMethodImpl()) {
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
