package org.restframework.web;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.misc.Pair;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.restframework.scanner.DirectoryScannerAdvanced;
import org.restframework.scanner.PackageScanner;
import org.restframework.scanner.ScannerApplication;
import org.restframework.web.annotations.gen.*;
import org.restframework.web.annotations.markers.CompilationComponent;
import org.restframework.web.annotations.markers.UpdateComponent;
import org.restframework.web.annotations.types.API;
import org.restframework.web.annotations.RestApi;
import org.restframework.web.annotations.types.FieldData;
import org.restframework.web.annotations.types.Model;
import org.restframework.web.core.AppRunner;
import org.restframework.web.core.RestAppConfigurationContext;
import org.restframework.web.core.generators.compilation.MethodImplementations;
import org.restframework.web.core.generics.Generic;
import org.restframework.web.core.RestApp;
import org.restframework.web.core.generators.MvcGenerator;
import org.restframework.web.core.generators.MvcSupport;
import org.restframework.web.core.templates.*;
import org.restframework.web.exceptions.RestException;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static org.restframework.web.core.RestConfigInit.*;
import static org.restframework.web.core.helpers.FileHelper.constructPath;
import static org.restframework.web.core.helpers.FileHelper.convertPackageToPath;

@Slf4j
@SuppressWarnings("unused")
@Data
public final class WebApp implements RestApp<WebApp> {

    public enum WebGenerationStrategy {
        WEB_REST_API_STRATEGY,
        WEB_CUSTOM_GENERATION_STRATEGY,
        NONE
    }

    // Internal Framework usage!
    private static @Nullable _APIBuilder builder;
    private static @Nullable RestApi restApiCtx;

    private static RestAppConfigurationContext context;
    private static ScannerApplication scannerApplication;

    private static RestApp<WebApp> internalApp;
    private static Object appContext;
    private static Class<?> classContext;
    private static WebGenerationStrategy buildStrategy;

    private static Pair<MethodImplementations, MethodImplementations> implementations;
    private static boolean defaultTemplatesFlag = false;

    private final static List<String> targetPaths = new ArrayList<>();
    private static String basePath;
    private static String basePackage;

    // For external usage!
    @Getter
    private ConfigurableApplicationContext springContext;

    public WebApp(@NotNull Class<?> clazz) throws UnsupportedEncodingException {
        log.info(":: Spring RESTframework Compiler ::\t\t\t(V1.2)\n\n");
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

        try {
            this.configurePaths(clazz);
        } catch (IllegalArgumentException e) {
            log.error("Rest compilation error - {}", e.getMessage());
            this.springContext = this.runSpring(WebApp.classContext);
        }
    }

    @Override
    public synchronized WebApp run() {
        if (this.springContext != null) return this;

        this.springContext = this.runSpring(WebApp.classContext);
        WebApp.scannerApplication = this.scannerApplication().run();

        try {
            this.init(WebApp.classContext);
        } catch (RestException |
                 InvocationTargetException |
                 InstantiationException |
                 IllegalAccessException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        log.warn("Make sure to implement the methods of the service and controller templates!");
        return this;
    }

    @Override
    public synchronized WebApp run(String[] args) {
        if (this.springContext != null) return this;

        this.springContext = this.runSpring(WebApp.classContext(), args);
        WebApp.scannerApplication = this.scannerApplication().run(args);

        try {
            this.init();
        } catch (RestException |
                 InvocationTargetException |
                 InstantiationException |
                 IllegalAccessException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        log.warn("Make sure to implement the methods of the service and controller templates!");
        return this;
    }

    public synchronized WebApp run(@NotNull AppRunner<RestApp<WebApp>> runnable) {
        if (this.springContext != null) return this;

        this.springContext = this.runSpring(WebApp.classContext());
        WebApp.scannerApplication = this.scannerApplication().run(new String[]{});

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
        log.warn("Make sure to implement the methods of the service and controller templates!");
        return this;
    }

    public WebApp methods(@NotNull MethodImplementations service, @NotNull MethodImplementations controller) {
        WebApp.implementations = new Pair<>(service, controller);
        return this;
    }

    private @NotNull ScannerApplication scannerApplication() {
        return new ScannerApplication(
                WebApp.classContext,
                WebApp.basePath,
                new PackageScanner(new DirectoryScannerAdvanced(
                        CompilationComponent.class,
                        UpdateComponent.class
                )));
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
        new GenerationHelper(
                new MvcGenerator(
                        new MvcSupportHandler()));
    }

    public static class GenerationHelper {

        private final MvcGenerator generator;

        public GenerationHelper(@NotNull MvcGenerator generator) {
            this.generator = generator;

            processGenComponents();
            processGenServices();
            switch (WebApp.buildStrategy) {
                case WEB_REST_API_STRATEGY -> {
                    assert WebApp.restApiCtx != null;
                    generateByUsingRestApiGenerationStrategy(WebApp.restApiCtx);
                }
                case WEB_CUSTOM_GENERATION_STRATEGY -> {
                    assert WebApp.builder != null;
                    generateByUsingCustomGenerationStrategy(WebApp.builder);
                }
            }
        }

        private void processGenComponents() {
            if (!WebApp.classContext().isAnnotationPresent(GenComponents.class)) return;
            final GenComponent[] componentAnnotations = WebApp.classContext().getAnnotationsByType(GenComponent.class);
            for (GenComponent component : componentAnnotations) {
                this.generator.generateComponent(component, WebApp.basePath);
            }
        }

        private void processGenServices() {
            if (!WebApp.classContext().isAnnotationPresent(GenServices.class)) return;
            final GenService[] componentAnnotations = WebApp.classContext().getAnnotationsByType(GenService.class);
            for (GenService service : componentAnnotations) {
                this.generator.generateService(service, WebApp.basePath);
            }
        }

        private void generateByUsingCustomGenerationStrategy(@NotNull _APIBuilder builder) {
            API api = builder.toAPI();
            if (!builder.nullCheckSpringComponents()) return;
            GenSpring spring = WebApp.classContext().getAnnotation(GenSpring.class);
            final Class<?>[] templates = {spring.controller(), spring.repo(), spring.service()};
            this.checkAndGenerateMVC(api, templates);
        }

        private void generateByUsingRestApiGenerationStrategy(@NotNull RestApi restApi) {
            final Class<?>[] templates = {restApi.controller(), restApi.repo(), restApi.service()};
            this.checkAndGenerateMVC(restApi.APIS(), templates);
        }

        private void checkAndGenerateMVC(API[] apis, Class<?>[] templates) {
            for (int i = 0; i < apis.length; i++) {
                API api = apis[i];
                this.checkConfigAndGenerateDao(api, WebApp.outputResultPathBase().get(i));
                this.generateMVC(api, templates, WebApp.outputResultPathBase().get(i));
            }
        }

        private void checkAndGenerateMVC(API api, Class<?>[] templates) {
            this.checkConfigAndGenerateDao(api, WebApp.basePath);
            this.generateMVC(api, templates, WebApp.basePath);
        }

        private void checkConfigAndGenerateDao(@NotNull API api, String buildpath) {
            if (!hasConfiguration(WebApp.classContext())) {
                this.generateDao(api, SpringComponents.MODEL, buildpath);
                this.generateDao(api, SpringComponents.DTO, buildpath);
            } else {
                this.generateDao(api, WebApp.context.getValueByKey(MODEL_COMPONENT_CONFIG_ID), buildpath);
                this.generateDao(api, WebApp.context.getValueByKey(DTO_COMPONENT_CONFIG_ID), buildpath);
            }
        }

        private void generateDao(API api, SpringComponents component) {
            this.generator.generateDao(api, component, WebApp.basePath);
        }

        private void generateDao(API api, SpringComponents component, String outputPath) {
            this.generator.generateDao(api, component, outputPath);
        }

        private void generateMVC(API api, Class<?>[] templates, String basePath) {
            if (this.checkMethodImpl(templates)) WebApp.defaultTemplatesFlag = true;
            for (Class<?> template : templates) this.generator.generateMVC(api, template, basePath);
        }

        private void generateStandaloneService() {

        }

        private boolean checkMethodImpl(Class<?> @NotNull [] templates) {
            if (templates.length > 3) throw new RestException("Too many templates used [" + templates.length + "] make sure to only use a max of three, service, repo & controller");

            Set<Class<?>> templateSet = new HashSet<>();
            Collections.addAll(templateSet, templates);

            return (templateSet.contains(TControllerCRUD.class) || templateSet.contains(TControllerEntityResponse.class) || templateSet.contains(TControllerEntityResponseWildcard.class))
                    && (templateSet.contains(TServiceCRUD.class) || templateSet.contains(TServiceEntityResponse.class) || templateSet.contains(TServiceEntityResponseWildcard.class))
                    && templateSet.contains(TRepo.class);
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
                public String apiPackage() {
                    return propertiesCtx.basePackage();
                }

                @Override
                public String modelAbbrev() {
                    return getModelCtx().abbrev();
                }

                @Override
                public String dtoAbbrev() {
                    return getDtoCtx().abbrev();
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
                final String packagePath = convertPackageToPath(WebApp.restApiCtx.basePackage());
                WebApp.basePackage = WebApp.restApiCtx.basePackage();
                WebApp.basePath = constructPath(clazz, srcRoot, packagePath);
                for (API api : WebApp.restApiCtx.APIS()) {
                    String path = constructPath(clazz, srcRoot, String.format("%s/%s", packagePath, api.apiPackage()));
                    WebApp.targetPaths.add(path);
                }
            }
            case WEB_CUSTOM_GENERATION_STRATEGY -> {
                assert WebApp.builder != null;
                API api = WebApp.builder.toAPI();
                WebApp.basePackage = api.apiPackage();
                WebApp.basePath = constructPath(clazz, srcRoot, convertPackageToPath(api.apiPackage()));
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

    public static RestApp<WebApp> internalApp() {
        return WebApp.internalApp;
    }

    public static List<String> outputResultPathBase() {
        return WebApp.targetPaths;
    }

    public static RestAppConfigurationContext configurations() {
        return WebApp.context;
    }

    public static MethodImplementations serviceMethods() {
        return WebApp.implementations.a;
    }

    public static ScannerApplication scannerApp() {
        return WebApp.scannerApplication;
    }

    public static MethodImplementations controllerMethods() {
        return WebApp.implementations.b;
    }

    public static WebGenerationStrategy strategy() {
        return WebApp.buildStrategy;
    }

    public static boolean defaultMethods() {
        return !(WebApp.implementations != null && !defaultTemplatesFlag);
    }

    public static String basePackage() {
        return WebApp.basePackage;
    }

    public static String determinePackage() {
        return WebApp.strategy() == WebApp.WebGenerationStrategy.WEB_REST_API_STRATEGY ? WebApp.context().basePackage() : WebApp.basePackage();
    }

    @NoArgsConstructor
    static class MvcSupportHandler implements MvcSupport {
        @Override
        public void call(@NotNull SpringComponents rules, String value) {
            ruleHolder.add(RestFrameworkAnnotations.COMPILATION_COMPONENT.getValue());
            switch (rules) {
                case CONTROLLER -> {
                    ruleHolder.add(LombokAnnotations.DATA.getValue());
                    ruleHolder.add(LombokAnnotations.ALL_ARGS_CONSTRUCTOR.getValue());
                    ruleHolder.add(SpringAnnotations.REST_CONTROLLER.getValue());
                    ruleHolder.add(this.makeRequestMapping(value));
                }
                case SERVICE -> {
                    ruleHolder.add(LombokAnnotations.DATA.getValue());
                    ruleHolder.add(LombokAnnotations.REQUIRED_ARGS_CONSTRUCTOR.getValue());
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
                    ruleHolder.add(LombokAnnotations.BUILDER.getValue());
                }
                case COMPONENT -> {
                    ruleHolder.add(SpringAnnotations.SPRING_COMPONENT.getValue());
                }
                case NONE -> throw new RestException("@" + RestApi.class + " MVC has no templates associated with it");
            }
        }
    }

    private void checkForErrorsInRestApiGeneratorStrategy(@NotNull Class<?> clazz) {
        if (!clazz.isAnnotationPresent(RestApi.class))
            throw new RestException("There must be a class annotated with @" + RestApi.class.getSimpleName() + "," +
                    "\nin order to run web framework.");

        if (clazz.isAnnotationPresent(GenModel.class))
            throw new RestException("The class cannot be annotated with both @" + RestApi.class.getSimpleName() +
                    "\nand @" + GenModel.class.getSimpleName());

        if (clazz.isAnnotationPresent(GenDto.class))
            throw new RestException("The class cannot be annotated with both @" + RestApi.class.getSimpleName() +
                    "\nand @" + GenDto.class.getSimpleName());

        if (clazz.isAnnotationPresent(GenDto.class)  && clazz.isAnnotationPresent(GenModel.class))
            throw new RestException("The class cannot be annotated with both @" + RestApi.class.getSimpleName() +
                    "\nand @" + GenDto.class.getSimpleName() + "and @" + GenModel.class.getSimpleName());
    }

    private void checkForErrorsInCustomApiGeneratorStrategy(@NotNull Class<?> clazz) {
        if (!clazz.isAnnotationPresent(GenProperties.class))
            throw new RestException("There must be a class annotated with @" + GenProperties.class.getSimpleName() + ",\n" +
                    "in order to make use of the custom generation strategy.\n" +
                    "Otherwise use standard generation with @" + RestApi.class.getSimpleName());

        if (clazz.getAnnotationsByType(GenProperties.class).length > 1)
            throw new RestException("There can only be ONE class annotated with @" + GenProperties.class.getSimpleName() + ",\n" +
                    "in order to make use of the custom generation strategy.");

        if (!clazz.isAnnotationPresent(GenModel.class))
            throw new RestException("There must be a class annotated with @" + GenModel.class.getSimpleName() + ",\n" +
                    "in order to make use of the custom generation strategy.");

        if (!clazz.isAnnotationPresent(GenDto.class))
            throw new RestException("There must be a class annotated with @" + GenDto.class.getSimpleName() + ",\n" +
                    "in order to make use of the custom generation strategy.");
    }
}