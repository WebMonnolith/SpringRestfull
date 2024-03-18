package org.restframework.web.core.generators;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.restframework.web.annotations.types.API;
import org.restframework.web.core.templates.SpringComponents;
import org.restframework.web.exceptions.RestException;

import static org.restframework.web.core.generators.GeneratorUtils.findTemplate;


@Slf4j
@AllArgsConstructor
public final class MvcGenerator {

    private final MvcSupport support;

    public synchronized void generateClasses(
            @NotNull API api,
            @NotNull Class<?> template,
            @NotNull String buildPath)
    {
        Generator<Class<?>> gen;
        switch (findTemplate(api, template).rule()) {
            case CONTROLLER -> gen = new ControllerGenerator(this.support);
            case SERVICE -> gen = new ServiceGenerator(this.support);
            case REPO -> gen = new RepoGenerator(this.support);
            default -> throw new RestException("Invalid configured component!");
        }

        gen.generate(api, template, buildPath);
    }

    public synchronized void generateByKey(
            @NotNull API api,
            @NotNull SpringComponents component,
            @NotNull String buildPath)
    {
        Generator<SpringComponents> gen;
        switch (component) {
            case MODEL -> gen = new ModelGenerator(this.support);
            case DTO -> gen = new DtoGenerator(this.support);
            default -> throw new RestException("Invalid configured component!");
        }

        gen.generate(api, component, buildPath);
    }
}


