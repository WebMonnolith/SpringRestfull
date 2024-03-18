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
            if (context.getMethods() != null) context.getMethods().build(context);
        }
    }
}
