package org.restframework.web.core.generators.compilation;

import org.restframework.web.core.builders.FieldBuilder;
import org.restframework.web.core.builders.Modifier;

public class ServiceCompilationStrategy implements CompilationStrategy {

    @Override
    public void execute(CompilationContext context) {
        context.getBuilder().addField(
                new FieldBuilder(
                        "repository",
                        context.getApi().basePackage() + ".repository." +
                                context.getApi().apiName() + "Repository",
                        Modifier.PRIVATE_FINAL));
    }
}
