package org.restframework.web.core.generators.compilation;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.restframework.web.core.templates.SpringComponents;

public class CompilationStrategyFactory {
    @Contract("_ -> new")
    public static @NotNull CompilationStrategy createStrategy(@NotNull CompilationContext context) {
        switch (context.getComponentType()) {
            case REPO -> {
                return new RepoCompilationStrategy();
            }
            case SERVICE -> {
                return new ServiceCompilationStrategy();
            }
            case COMPONENT -> {
                return new ComponentCompilationStrategy();
            }
            case CONTROLLER -> {
                return new ControllerCompilationStrategy();
            }
            default -> {
                return new DefaultCompilationStrategy();
            }
        }
    }
}
