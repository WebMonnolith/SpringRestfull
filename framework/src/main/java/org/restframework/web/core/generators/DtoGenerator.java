package org.restframework.web.core.generators;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.restframework.web.annotations.API;
import org.restframework.web.core.builders.ClassBuilder;
import org.restframework.web.core.builders.FieldBuilder;
import org.restframework.web.core.builders.Modifier;
import org.restframework.web.core.generators.compilation.CompilationContext;
import org.restframework.web.core.generators.compilation.CompilationFlags;
import org.restframework.web.core.generators.compilation.CompilationProcessor;
import org.restframework.web.core.generics.GenericFactory;
import org.restframework.web.core.generics.GenericGeneration;
import org.restframework.web.core.templates.ClassTypes;
import org.restframework.web.core.templates.DtoFrame;
import org.restframework.web.core.templates.ModelFrame;
import org.restframework.web.core.templates.SpringComponents;

import static org.restframework.web.core.helpers.FileHelper.NO_DIR;

@AllArgsConstructor
public class DtoGenerator extends Generator<SpringComponents> {
    private final MvcSupport support;

    @Override
    public void generate(@NotNull API api, @NotNull SpringComponents component, @NotNull String buildPath) {
        if (CompilationFlags.generateModelsOnce) return;
        CompilationFlags.useModelsApi = true;

        GenericGeneration genericResolver = GenericFactory.create(api.model().generic());

        String value = "";
        String name = api.model().apiName()+"Dto";

        ClassBuilder modelBuilder = new ClassBuilder(
                name,
                api.basePackage(),
                this.support.callInner(component, value),
                ClassTypes.CLASS,
                new ImportResolver(component, genericResolver.getImportStatement(), api.basePackage()).get());

        modelBuilder.addExtension(DtoFrame.class);

        this.compile(CompilationContext.builder()
                .api(api)
                .builder(modelBuilder)
                .modelAnnotation(api.model())
                .dtoName(api.model().apiName()+"Dto")
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