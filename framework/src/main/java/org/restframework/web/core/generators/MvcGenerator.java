package org.restframework.web.core.generators;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.misc.Pair;
import org.restframework.web.annotations.*;
import org.restframework.web.core.builders.Modifier;
import org.restframework.web.core.builders.ClassBuilder;
import org.restframework.web.core.builders.FieldBuilder;
import org.restframework.web.core.generators.compilation.CompilationContext;
import org.restframework.web.core.generators.compilation.CompilationFlags;
import org.restframework.web.core.generators.compilation.CompilationProcessor;
import org.restframework.web.core.generics.GenericFactory;
import org.restframework.web.core.generics.GenericGeneration;
import org.restframework.web.core.templates.ClassTypes;
import org.restframework.web.core.templates.DtoFrame;
import org.restframework.web.core.templates.ModelFrame;
import org.restframework.web.core.templates.SpringComponents;
import org.restframework.web.exceptions.RestException;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.annotation.AnnotationUtils;

import static org.restframework.web.core.helpers.FileHelper.NO_DIR;


@Slf4j
@AllArgsConstructor
public final class MvcGenerator {

    private final MvcSupport support;

    public synchronized void generateClasses(
            @NotNull API api,
            @NotNull Class<?> template,
            @NotNull String buildPath)
    {
        Generator<Class<?>> gen = new ComponentGenerator(this.support);
        gen.generate(api, template, buildPath);
    }

    public synchronized void generateByKey(
            @NotNull API api,
            boolean flag,
            @NotNull SpringComponents component,
            @NotNull String buildPath)
    {
        Generator<SpringComponents> gen;
        switch (component) {
            case MODEL -> gen = new ModelGenerator(this.support);
            case DTO -> gen = new DtoGenerator(this.support);
            default -> throw new RestException("Invalid component!");
        }

        gen.generate(api, component, buildPath);
    }
}


