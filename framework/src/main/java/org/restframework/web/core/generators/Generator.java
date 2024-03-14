package org.restframework.web.core.generators;

import org.jetbrains.annotations.NotNull;
import org.restframework.web.annotations.types.API;
import org.restframework.web.core.generators.compilation.CompilationContext;

public abstract class Generator<T> {
    public abstract void generate(
            @NotNull API api,
            @NotNull T type,
            @NotNull String buildPath
    );

    protected abstract void compile(@NotNull CompilationContext context);
}
