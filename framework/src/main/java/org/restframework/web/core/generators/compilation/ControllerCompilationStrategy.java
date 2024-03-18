package org.restframework.web.core.generators.compilation;

import org.jetbrains.annotations.NotNull;
import org.restframework.web.core.builders.FieldBuilder;
import org.restframework.web.core.builders.MethodBuilder;
import org.restframework.web.core.builders.Modifier;

public class ControllerCompilationStrategy implements CompilationStrategy {

    @Override
    public void execute(@NotNull CompilationContext context) {
        String serviceID = context.getApi().apiName().toLowerCase() + "Service";

        context.getBuilder().addField(
                new FieldBuilder(
                        serviceID,
                        context.getApi().apiName() + "Service",
                        Modifier.PRIVATE_FINAL));

        if (context.isDefaultTemplateMethodImpl()) {
            if (context.getMethods() != null) context.getMethods().build(context);
        }
    }
}
