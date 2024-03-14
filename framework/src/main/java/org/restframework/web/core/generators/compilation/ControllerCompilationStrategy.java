package org.restframework.web.core.generators.compilation;

import org.jetbrains.annotations.NotNull;
import org.restframework.web.core.builders.FieldBuilder;
import org.restframework.web.core.builders.Modifier;

public class ControllerCompilationStrategy implements CompilationStrategy {

    @Override
    public void execute(@NotNull CompilationContext context) {
        context.getBuilder().addField(
                new FieldBuilder(
                        context.getApi().apiName().toLowerCase() + "Service",
                        context.getApi().apiName() + "Service",
                        Modifier.PRIVATE_FINAL));

        if (context.isDefaultTemplateMethodImpl()) {
            // TODO Add method building construction
        }
    }
}
