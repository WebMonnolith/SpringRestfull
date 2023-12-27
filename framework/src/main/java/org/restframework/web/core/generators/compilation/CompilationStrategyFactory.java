package org.restframework.web.core.generators.compilation;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.restframework.web.core.templates.SpringComponents;

public class CompilationStrategyFactory {
    @Contract("_ -> new")
    public static @NotNull CompilationStrategy createStrategy(@NotNull CompilationContext context) {
        if (context.getTemplateAnnotation().rule() == SpringComponents.SERVICE)
            return new ServiceCompilationStrategy();
        else if (context.getTemplateAnnotation().rule() == SpringComponents.CONTROLLER)
            return new ControllerCompilationStrategy();
        else if (context.getTemplateAnnotation().rule() == SpringComponents.REPO)
            return new RepoCompilationStrategy();
        else
            return new DefaultCompilationStrategy();
    }
}
