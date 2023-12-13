package org.restframework.web;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.restframework.web.annotations.RestApi;
import org.restframework.web.core.AppRunner;
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

@Slf4j
@SuppressWarnings("unused")
@Data
public final class WebApp implements RestApp {

    private static RestApp internalApp;
    private static RestApi info;
    private static Object appContext;
    private static Class<?> classContext;
    private static String targetPath;

    public WebApp(@NotNull Class<?> clazz) {
        WebApp.info = clazz.getAnnotation(RestApi.class);
        if (!clazz.isAnnotationPresent(RestApi.class))
            throw new RestException("There must be a class annotated with @" + RestApi.class + ", in order to run web framework.");

        WebApp.targetPath = FileHelper.constructPath(
                clazz, "/src/main/java",
                FileHelper.convertPackageToPath(WebApp.info.basePackage()));

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

    private static void generate(@NotNull RestApi api) {
        if (WebApp.checkContext(api)) {
            for (Class<?> template : api.templates()) {
                MvcGenerator.generateClasses(api, template, new MvcSupportHandler());
            }
            MvcGenerator.generateModels(api, true);
        }
    }

    static class MvcSupportHandler implements MvcSupport {
        @Override
        public void call(SpringComponents rules, String value) {
            if (!ruleHolder.isEmpty())
                ruleHolder.clear();

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
                case NONE -> throw new RestException("@" + RestApi.class + " MVC has no templates associated with it");
            }
        }
    }

    private static boolean compareContext(@NotNull RestApi rest) {
        int len = rest.apiNames().length;
        if (rest.endpoints().length == len && rest.models().length == len) return true;
        throw new RestException(
                String.format("Length mismatch of internal array types: names()=%d, endpoints()=%d, options()=%d",
                len, rest.endpoints().length, rest.models().length)
        );
    }

    private static boolean checkContext(@NotNull RestApi rest) {
        if (compareContext(rest)) return true;
        throw new RestException("@RestApp exception!");
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

    public static String outputResultPathBase() {
        return WebApp.targetPath;
    }

}