package org.restframework.web;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.restframework.web.annotations.API;
import org.restframework.web.annotations.RestApi;
import org.restframework.web.core.AppRunner;
import org.restframework.web.core.RestAppConfigurationContext;
import org.restframework.web.core.helpers.FileHelper;
import org.restframework.web.core.RestApp;
import org.restframework.web.core.generators.MvcGenerator;
import org.restframework.web.core.generators.MvcSupport;
import org.restframework.web.core.templates.SpringComponents;
import org.restframework.web.exceptions.RestException;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static org.restframework.web.core.RestConfigInit.configure;
import static org.restframework.web.core.RestConfigInit.hasConfiguration;

@Slf4j
@SuppressWarnings("unused")
@Data
public final class WebApp implements RestApp {

    private static RestAppConfigurationContext context;
    private static RestApp internalApp;
    private static RestApi info;
    private static Object appContext;
    private static Class<?> classContext;
    private final static List<String> targetPaths = new ArrayList<>();

    public WebApp(@NotNull Class<?> clazz) {
        WebApp.info = clazz.getAnnotation(RestApi.class);
        if (!clazz.isAnnotationPresent(RestApi.class))
            throw new RestException("There must be a class annotated with @" + RestApi.class + ", in order to run web framework.");

        WebApp.context = configure(clazz);

        String srcRoot;

        if (hasConfiguration(clazz)) {
            assert WebApp.context != null;
            srcRoot = WebApp.context.getValueByKey("content-root");
        }
        else {
            srcRoot = "/src/main/java";
        }

        for (API api : WebApp.info.APIS())
            WebApp.targetPaths.add(FileHelper.constructPath(clazz, srcRoot, FileHelper.convertPackageToPath(api.basePackage())));
    }

    @Override
    public synchronized <T> void run(@NotNull Class<T> clazz) {
        WebApp.runSpring(clazz);
        try {
            WebApp.init(clazz);
        } catch (RestException |
                 InvocationTargetException |
                 InstantiationException |
                 IllegalAccessException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized <T> void run(@NotNull Class<T> clazz, String[] args) {
        WebApp.runSpring(clazz, args);
        try {
            WebApp.init(clazz);
        } catch (RestException |
                 InvocationTargetException |
                 InstantiationException |
                 IllegalAccessException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized <T> void run(@NotNull Class<T> clazz, @NotNull AppRunner<RestApp> runnable) {
        WebApp.runSpring(clazz);
        try {
            WebApp.init(clazz);
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
    public synchronized <T> void run(@NotNull Class<T> clazz, String[] args, @NotNull AppRunner<RestApp> runnable) {
        WebApp.runSpring(clazz);
        try {
            WebApp.init(clazz, args);
        } catch (RestException |
                 InvocationTargetException |
                 InstantiationException |
                 IllegalAccessException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        WebApp.internalApp = runnable.call(WebApp.classContext());
    }

    private static <T> void init(@NotNull Class<T> clazz)
            throws RestException,
            NoSuchMethodException,
            InvocationTargetException,
            InstantiationException,
            IllegalAccessException
    {
        WebApp.classContext = clazz;
        WebApp.appContext = WebApp.classContext.getDeclaredConstructor().newInstance();

        WebApp.generate(WebApp.info);
    }

    @SafeVarargs
    private static <T, args> void init(@NotNull Class<T> clazz, args ... params)
            throws RestException,
            NoSuchMethodException,
            InvocationTargetException,
            InstantiationException,
            IllegalAccessException
    {

        WebApp.classContext = clazz;
        WebApp.appContext = WebApp.classContext.getDeclaredConstructor().newInstance();

        WebApp.generate(WebApp.info);
    }

    private synchronized static <T> ConfigurableApplicationContext runSpring(@NotNull Class<T> clazz) {
        return SpringApplication.run(clazz);
    }

    private synchronized static <T> ConfigurableApplicationContext runSpring(@NotNull Class<T> clazz, String[] args) {
        return SpringApplication.run(clazz, args);
    }

    private static void generate(@NotNull RestApi restApi) {
        for (int i = 0; i < restApi.APIS().length; i++) {
            API api = restApi.APIS()[i];
            for (Class<?> template : restApi.templates())
                MvcGenerator.generateClasses(api, template, new MvcSupportHandler(), WebApp.outputResultPathBase().get(i));
            MvcGenerator.generateByKey(api, false, WebApp.context.getValueByKey("model-generation"), WebApp.outputResultPathBase().get(i));
            MvcGenerator.generateByKey(api, false, WebApp.context.getValueByKey("dto-generation"), WebApp.outputResultPathBase().get(i));
        }
    }

    public static RestApi context() {
        return WebApp.info;
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
                case DTO -> {}
                case NONE -> throw new RestException("@" + RestApi.class + " MVC has no templates associated with it");
            }
        }
    }

}