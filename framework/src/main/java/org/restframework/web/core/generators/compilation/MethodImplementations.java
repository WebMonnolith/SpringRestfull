package org.restframework.web.core.generators.compilation;

import org.jetbrains.annotations.NotNull;
import org.restframework.web.core.builders.MethodBuilder;

@SuppressWarnings("unused")
@FunctionalInterface
public interface MethodImplementations {
    void build(@NotNull CompilationContext context);
}
