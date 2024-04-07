package org.restframework.web.core.generators;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.restframework.web.annotations.gen.GenComponent;
import org.restframework.web.annotations.types.API;
import org.restframework.web.core.templates.SpringComponents;
import org.restframework.web.exceptions.RestException;

import java.lang.annotation.Annotation;

import static org.restframework.web.core.generators.GeneratorUtils.findTemplate;


@Slf4j
@AllArgsConstructor
public final class MvcGenerator {

    private final MvcSupport support;

    public synchronized void generateMVC(
            @NotNull API api,
            @NotNull Class<?> template,
            @NotNull String buildPath
    ) {
        Generator<Class<?>> gen;
        switch (findTemplate(api, template).rule()) {
            case CONTROLLER -> gen = new ControllerGenerator(this.support);
            case SERVICE -> gen = this.generateService(api, template, buildPath);
            case REPO -> gen = new RepoGenerator(this.support);
            default -> throw new RestException("Invalid configured component!");
        }

        gen.generate(api, template, buildPath);
    }

    public synchronized void generateComponent(
            @NotNull GenComponent component,
            @NotNull String buildPath
    ) {
        Generator<GenComponent> gen = new ComponentGenerator(this.support);
        gen.generate(null, component, buildPath);
    }

    private synchronized Generator<Class<?>> generateService(
            @NotNull API api,
            @NotNull Class<?> template,
            @NotNull String buildPath
    ) {
        Generator<Class<?>> serviceGen = new ServiceGenerator(this.support);
        return serviceGen;
    }

    public synchronized void generateDao(
            @NotNull API api,
            @NotNull SpringComponents component,
            @NotNull String buildPath
    ) {
        Generator<SpringComponents> gen;
        switch (component) {
            case MODEL -> gen = new ModelGenerator(this.support);
            case DTO -> gen = new DtoGenerator(this.support);
            default -> throw new RestException("Invalid configured component!");
        }

        gen.generate(api, component, buildPath);
    }
}


