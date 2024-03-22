package org.restframework.web.core.generators;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.restframework.web.annotations.gen.GenComponent;
import org.restframework.web.annotations.types.API;
import org.restframework.web.core.generators.compilation.CompilationContext;

@RequiredArgsConstructor
public class ComponentGenerator extends Generator<GenComponent> {
    private final MvcSupport support;

    @Override
    public void generate(@Nullable API api, @NotNull GenComponent type, @NotNull String buildPath) {

    }

    @Override
    protected void compile(@NotNull CompilationContext context) {

    }
}
