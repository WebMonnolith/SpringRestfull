package org.restframework.web.core.generators;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.restframework.web.WebApp;
import org.restframework.web.annotations.gen.GenComponent;
import org.restframework.web.annotations.types.API;
import org.restframework.web.core.builders.ClassBuilder;
import org.restframework.web.core.generators.compilation.CompilationContext;
import org.restframework.web.core.generators.compilation.CompilationFlags;
import org.restframework.web.core.generators.compilation.CompilationProcessor;
import org.restframework.web.core.generics.GenericFactory;
import org.restframework.web.core.generics.GenericGeneration;
import org.restframework.web.core.templates.ClassTypes;
import org.restframework.web.core.templates.SpringComponents;

import static org.restframework.web.WebApp.defaultMethods;

@RequiredArgsConstructor
public class ComponentGenerator extends Generator<GenComponent> {
    private final MvcSupport support;

    @Override
    public void generate(@Nullable API api, @NotNull GenComponent type, @NotNull String buildPath) {
        CompilationFlags.useModelsApi = false;

        String packageName = "";
        switch (WebApp.strategy()) {
            case WEB_REST_API_STRATEGY -> packageName = "%s.%s".formatted(WebApp.context().basePackage(), type.packageName());
            case WEB_CUSTOM_GENERATION_STRATEGY ->  packageName = type.packageName();
        }

        ClassBuilder componentBuilder = new ClassBuilder(
                type.name()+type.abbrev(),
                "%s.components".formatted(packageName),
                support.callInner(SpringComponents.COMPONENT, ""),
                ClassTypes.CLASS,
                new ImportResolver(SpringComponents.COMPONENT, packageName).get());

        this.compile(CompilationContext.builder()
                .api(api)
                .builder(componentBuilder)
                .componentType(SpringComponents.COMPONENT)
                .build());

        componentBuilder.build(buildPath, "components");
    }

    @Override
    protected void compile(@NotNull CompilationContext context) {
        CompilationProcessor.compile(context);
    }
}
