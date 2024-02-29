package org.restframework.web;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.restframework.web.annotations.gen.GenDto;
import org.restframework.web.annotations.gen.GenModel;
import org.restframework.web.annotations.gen.GenProperties;
import org.restframework.web.annotations.gen.GenSpring;
import org.restframework.web.annotations.types.API;
import org.restframework.web.annotations.RestApi;
import org.restframework.web.annotations.types.FieldData;
import org.restframework.web.annotations.types.Model;
import org.restframework.web.core.AppRunner;
import org.restframework.web.core.RestAppConfigurationContext;
import org.restframework.web.core.generics.Generic;
import org.restframework.web.core.helpers.FileHelper;
import org.restframework.web.core.RestApp;
import org.restframework.web.core.generators.MvcGenerator;
import org.restframework.web.core.generators.MvcSupport;
import org.restframework.web.core.templates.SpringComponents;
import org.restframework.web.exceptions.RestException;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static org.restframework.web.core.RestConfigInit.*;

@Slf4j
@SuppressWarnings("unused")
@Data
public final class WebApp implements RestApp {

    private enum WebGenerationStrategy {
        WEB_REST_API_STRATEGY,
        WEB_CUSTOM_GENERATION_STRATEGY,
        NONE
    }

    private static @Nullable _APIBuilder builder;
    private static @Nullable RestApi restApiCtx;

    private static RestAppConfigurationContext context;
    private static RestApp internalApp;
    private static Object appContext;
    private static Class<?> classContext;
    private static WebGenerationStrategy buildStrategy;
    private final static List<String> targetPaths = new ArrayList<>();

    public WebApp(@NotNull Class<?> clazz) throws UnsupportedEncodingException {
        WebApp.buildStrategy = this.start(clazz);
        switch (WebApp.buildStrategy) {
            case WEB_REST_API_STRATEGY -> {
                checkForErrorsInRestApiGeneratorStrategy(clazz);
                WebApp.restApiCtx = clazz.getAnnotation(RestApi.class);
            }
            case WEB_CUSTOM_GENERATION_STRATEGY ->  {
                checkForErrorsInCustomApiGeneratorStrategy(clazz);
                WebApp.builder = _APIBuilder.builder()
                        .dtoCtx(clazz.getAnnotation(GenDto.class))
                        .modelCtx(clazz.getAnnotation(GenModel.class))
                        .propertiesCtx(clazz.getAnnotation(GenProperties.class))
                        .springCtx(clazz.getAnnotation(GenSpring.class))
                        .build();
            }
        }

        this.configurePaths(clazz);
    }

    @Override
    public synchronized <T> void run(@NotNull Class<T> clazz) {
        this.runSpring(clazz);
        try {
            this.init(clazz);
        } catch (RestException |
                 InvocationTargetException |
                 InstantiationException |
                 IllegalAccessException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized <T> void run(String[] args) {
        this.runSpring(WebApp.classContext(), args);
        try {
            this.init();
        } catch (RestException |
                 InvocationTargetException |
                 InstantiationException |
                 IllegalAccessException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized <T> void run(@NotNull AppRunner<RestApp> runnable) {
        this.runSpring(WebApp.classContext());
        try {
            this.init();
        } catch (RestException |
                 InvocationTargetException |
                 InstantiationException |
                 IllegalAccessException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        WebApp.internalApp = runnable.call(WebApp.classContext());
    }

    @Override
    public synchronized <T> void run(String[] args, @NotNull AppRunner<RestApp> runnable) {
        this.runSpring(WebApp.classContext());
        try {
            this.init(args);
        } catch (RestException |
                 InvocationTargetException |
                 InstantiationException |
                 IllegalAccessException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        WebApp.internalApp = runnable.call(WebApp.classContext());
    }

    private <T> void init()
            throws RestException,
            NoSuchMethodException,
            InvocationTargetException,
            InstantiationException,
            IllegalAccessException
    {
        WebApp.appContext = WebApp.classContext.getDeclaredConstructor().newInstance();
        this.generate();
    }

    @SafeVarargs
    private <T, args> void init(args ... params)
            throws RestException,
            NoSuchMethodException,
            InvocationTargetException,
            InstantiationException,
            IllegalAccessException
    {

        WebApp.appContext = WebApp.classContext.getDeclaredConstructor().newInstance();
        this.generate();
    }

    private void generate() {
        switch (WebApp.buildStrategy) {
            case WEB_REST_API_STRATEGY -> {
                assert WebApp.restApiCtx != null;
                this.generateByUsingRestApiGenerationStrategy(WebApp.restApiCtx);
            }
            case WEB_CUSTOM_GENERATION_STRATEGY -> {
                assert WebApp.builder != null;
                this.generateByUsingCustomGenerationStrategy(WebApp.builder);
            }
        }
    }

    private void generateByUsingCustomGenerationStrategy(@NotNull _APIBuilder builder) {
        API api = builder.toAPI();
        MvcGenerator generator = new MvcGenerator(new MvcSupportHandler());

        if (!hasConfiguration(WebApp.classContext())) {
            generator.generateByKey(
                    api, SpringComponents.MODEL, WebApp.outputResultPathBase().get(0));
            generator.generateByKey(
                    api, SpringComponents.DTO, WebApp.outputResultPathBase().get(0));
        }
        else {
            generator.generateByKey(
                    api, WebApp.context.getValueByKey(MODEL_COMPONENT_CONFIG_ID), WebApp.outputResultPathBase().get(0));
            generator.generateByKey(
                    api, WebApp.context.getValueByKey(DTO_COMPONENT_CONFIG_ID), WebApp.outputResultPathBase().get(0));
        }

        if (!builder.nullCheckSpringComponents()) return;
        for (Class<?> template : builder.springCtx.templates())
            generator.generateClasses(api, template, WebApp.outputResultPathBase().get(0));
    }

    private void generateByUsingRestApiGenerationStrategy(@NotNull RestApi restApi) {
        MvcGenerator generator = new MvcGenerator(new MvcSupportHandler());
        for (int i = 0; i < restApi.APIS().length; i++) {
            API api = restApi.APIS()[i];
            if (!hasConfiguration(WebApp.classContext())) {
                generator.generateByKey(
                        api, SpringComponents.MODEL, WebApp.outputResultPathBase().get(i));
                generator.generateByKey(
                        api, SpringComponents.DTO, WebApp.outputResultPathBase().get(i));
            }
            else {
                generator.generateByKey(
                        api, WebApp.context.getValueByKey(MODEL_COMPONENT_CONFIG_ID), WebApp.outputResultPathBase().get(i));
                generator.generateByKey(
                        api, WebApp.context.getValueByKey(DTO_COMPONENT_CONFIG_ID), WebApp.outputResultPathBase().get(i));
            }
            for (Class<?> template : restApi.templates())
                generator.generateClasses(api, template, WebApp.outputResultPathBase().get(i));
        }
    }

    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    private static class _ModelBuilder {
        private GenModel modelCtx;
        private GenProperties propertiesCtx;

        public @NotNull Model toModel() {
            return new Model() {
                @Override
                public Class<? extends Annotation> annotationType() {
                    return Model.class;
                }

                @Override
                public Generic generic() {
                    return getPropertiesCtx().indexColumnType();
                }

                @Override
                public String tableName() {
                    return getModelCtx().tableName();
                }

                @Override
                public String apiName() {
                    return getPropertiesCtx().apiName();
                }

                @Override
                public String abbrev() {
                    return "Model";
                }

                @Override
                public FieldData[] fields() {
                    return getModelCtx().fields();
                }
            };
        }
    }

    @NoArgsConstructor
    @Builder
    @Getter
    @Setter
    private static class _APIBuilder {
        private GenDto dtoCtx;
        private GenModel modelCtx;
        private GenProperties propertiesCtx;
        private GenSpring springCtx;

        public _APIBuilder(
                @NotNull GenDto dtoCtx,
                @NotNull GenModel modelCtx,
                @NotNull GenProperties propertiesCtx,
                GenSpring springCtx
        ) {
            this.dtoCtx = dtoCtx;
            this.modelCtx = modelCtx;
            this.springCtx = springCtx;
            this.propertiesCtx = propertiesCtx;
        }

        public boolean nullCheckSpringComponents() {
            return this.springCtx != null;
        }

        @Contract(value = " -> new", pure = true)
        public @NotNull API toAPI() {
            _ModelBuilder modelBuilder = _ModelBuilder.builder()
                    .modelCtx(this.modelCtx)
                    .propertiesCtx(this.propertiesCtx)
                    .build();
            return new API() {
                @Override
                public Class<? extends Annotation> annotationType() {
                    return API.class;
                }

                @Override
                public String apiName() {
                    return getPropertiesCtx().apiName();
                }

                @Override
                public String endpoint() {
                    return getPropertiesCtx().endpoint();
                }

                @Override
                public Model model() {
                    return modelBuilder.toModel();
                }

                @Override
                public String basePackage() {
                    return propertiesCtx.basePackage();
                }
            };
        }
    }

    private WebGenerationStrategy start(@NotNull Class<?> clazz) {
        WebApp.context = configure(clazz);
        WebApp.classContext = clazz;
        return this.determineWebAppGenerationStrategy(clazz);
    }

    private WebGenerationStrategy determineWebAppGenerationStrategy(@NotNull Class<?> clazz) {
        if (!hasConfiguration(clazz)) return WebGenerationStrategy.WEB_REST_API_STRATEGY;

        if (WebApp.context.getValueByKey(CUSTOM_GENERATION_CONFIG_ID))
            return WebGenerationStrategy.WEB_CUSTOM_GENERATION_STRATEGY;
        return WebGenerationStrategy.WEB_REST_API_STRATEGY;
    }

    private void configurePaths(Class<?> clazz) throws UnsupportedEncodingException {
        String srcRoot = this.getSourceRoot(clazz);

        switch (WebApp.buildStrategy) {
            case WEB_REST_API_STRATEGY -> {
                assert WebApp.restApiCtx != null;
                for (API api : WebApp.restApiCtx.APIS()) {
                    String path = FileHelper.constructPath(clazz, srcRoot, FileHelper.convertPackageToPath(api.basePackage()));
                    WebApp.targetPaths.add(path);
                }
            }
            case WEB_CUSTOM_GENERATION_STRATEGY -> {
                assert WebApp.builder != null;
                API api = WebApp.builder.toAPI();
                String path = FileHelper.constructPath(clazz, srcRoot, FileHelper.convertPackageToPath(api.basePackage()));
                WebApp.targetPaths.add(path);
            }
        }
    }

    private String getSourceRoot(Class<?> clazz) {
        String srcRoot;

        if (hasConfiguration(clazz)) {
            assert WebApp.context != null;
            srcRoot = WebApp.context.getValueByKey(CONTENT_ROOT_CONFIG_ID);
        } else {
            srcRoot = "/src/main/java";
        }

        return srcRoot;
    }

    private synchronized <T> ConfigurableApplicationContext runSpring(@NotNull Class<T> clazz) {
        return SpringApplication.run(clazz);
    }

    private synchronized <T> ConfigurableApplicationContext runSpring(@NotNull Class<T> clazz, String[] args) {
        return SpringApplication.run(clazz, args);
    }

    public static RestApi context() {
        return WebApp.restApiCtx;
    }

    public static Object appContext() {
        return WebApp.appContext;
    }

    public static Class<?> classContext() {
        return WebApp.classContext;
    }

    public static RestApp internalApp() {
        return WebApp.internalApp;
    }

    public static List<String> outputResultPathBase() {
        return WebApp.targetPaths;
    }

    public static RestAppConfigurationContext configurations() {
        return WebApp.context;
    }

    @NoArgsConstructor
    static class MvcSupportHandler implements MvcSupport {
        @Override
        public void call(@NotNull SpringComponents rules, String value) {
            switch (rules) {
                case CONTROLLER -> {
                    ruleHolder.add(LombokAnnotations.DATA.getValue());
                    ruleHolder.add(LombokAnnotations.ALL_ARGS_CONSTRUCTOR.getValue());
                    ruleHolder.add(SpringAnnotations.REST_CONTROLLER.getValue());
                    ruleHolder.add(this.makeRequestMapping(value));
                }
                case SERVICE -> {
                    ruleHolder.add(LombokAnnotations.DATA.getValue());
                    ruleHolder.add(LombokAnnotations.ALL_ARGS_CONSTRUCTOR.getValue());
                    ruleHolder.add(SpringAnnotations.SERVICE.getValue());
                }
                case REPO -> ruleHolder.add(SpringAnnotations.REPOSITORY.getValue());
                case MODEL -> {
                    ruleHolder.add(LombokAnnotations.EQUALS_AND_HASHCODE.getValue());
                    ruleHolder.add(LombokAnnotations.DATA.getValue());
                    ruleHolder.add(LombokAnnotations.ALL_ARGS_CONSTRUCTOR.getValue());
                    ruleHolder.add(LombokAnnotations.NO_ARGS_CONSTRUCTOR.getValue());
                    ruleHolder.add(LombokAnnotations.BUILDER.getValue());
                    ruleHolder.add(PersistenceAnnotations.ENTITY.getValue());
                    ruleHolder.add(this.makeTable(value));
                }
                case DTO -> {
                    ruleHolder.add(LombokAnnotations.EQUALS_AND_HASHCODE.getValue());
                    ruleHolder.add(LombokAnnotations.DATA.getValue());
                    ruleHolder.add(LombokAnnotations.ALL_ARGS_CONSTRUCTOR.getValue());
                    ruleHolder.add(LombokAnnotations.NO_ARGS_CONSTRUCTOR.getValue());
                    ruleHolder.add(LombokAnnotations.BUILDER.getValue());
                }
                case NONE -> throw new RestException("@" + RestApi.class + " MVC has no templates associated with it");
            }
        }
    }

    private void checkForErrorsInRestApiGeneratorStrategy(@NotNull Class<?> clazz) {
        if (!clazz.isAnnotationPresent(RestApi.class))
            throw new RestException("There must be a class annotated with @" + RestApi.class + ", in order to run web framework.");

        if (clazz.isAnnotationPresent(GenModel.class))
            throw new RestException("The class cannot be annotated with both @" + RestApi.class + " and @" + GenModel.class);

        if (clazz.isAnnotationPresent(GenDto.class))
            throw new RestException("The class cannot be annotated with both @" + RestApi.class + " and @" + GenDto.class);

        if (clazz.isAnnotationPresent(GenDto.class)  && clazz.isAnnotationPresent(GenModel.class))
            throw new RestException("The class cannot be annotated with both @" + RestApi.class + " and @" + GenDto.class + "and @" + GenModel.class);
    }

    private void checkForErrorsInCustomApiGeneratorStrategy(@NotNull Class<?> clazz) {

    }
}