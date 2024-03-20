package org.restframework.web.core.generators;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.restframework.web.WebApp;
import org.restframework.web.annotations.types.API;
import org.restframework.web.core.builders.ClassBuilder;
import org.restframework.web.core.generators.compilation.CompilationContext;
import org.restframework.web.core.generators.compilation.CompilationFlags;
import org.restframework.web.core.generators.compilation.CompilationProcessor;
import org.restframework.web.core.generics.GenericFactory;
import org.restframework.web.core.generics.GenericGeneration;
import org.restframework.web.core.templates.ClassTypes;
import org.restframework.web.core.templates.DtoFrame;
import org.restframework.web.core.templates.SpringComponents;

import static org.restframework.web.core.helpers.FileHelper.NO_DIR;

@RequiredArgsConstructor
public class DtoGenerator extends Generator<SpringComponents> {
    private final MvcSupport support;

    @Override
    public void generate(
            @NotNull API api,
            @NotNull SpringComponents component,
            @NotNull String buildPath
    ) {
        if (CompilationFlags.generateModelsOnce) return;
        CompilationFlags.useModelsApi = true;

        GenericGeneration genericResolver = GenericFactory.create(api.model().generic());

        String value = "";
        String name = api.apiName()+"Dto";
        String packageName = "";
        switch (WebApp.strategy()) {
            case WEB_REST_API_STRATEGY -> packageName = String.format("%s.%s", WebApp.context().basePackage(), api.apiPackage());
            case WEB_CUSTOM_GENERATION_STRATEGY ->  packageName = api.apiPackage();
        }

        ClassBuilder modelBuilder = new ClassBuilder(
                name,
                packageName,
                this.support.callInner(component, value),
                ClassTypes.CLASS,
                new ImportResolver(
                        component,
                        genericResolver.getImportStatement(),
                        packageName)
                        .get()
        );

        modelBuilder.addExtension(DtoFrame.class);

        this.compile(CompilationContext.builder()
                .api(api)
                .builder(modelBuilder)
                .modelAnnotation(api.model())
                .dtoName(api.apiName()+"Dto")
                .generic(genericResolver.getGeneric())
                .build()
        );

        modelBuilder.build(buildPath, NO_DIR);


//        if (flag) {
//            CompilationFlags.generateModelsOnce = true;
//        }
    }

    @Override
    protected void compile(@NotNull CompilationContext context) {
        CompilationProcessor.compile(context);
    }
}
