package org.restframework.web.core.generators.builders;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@SuppressWarnings("unused")
public interface BuilderUtils {
    default void addAnnotation(@NotNull Class<?> clazz) {}
    default <T> void addAnnotation(@NotNull T annotation) {}
    default void addInterface(@NotNull Class<?> clazz) {}
    default void addInterface(@NotNull Class<?> clazz, String @NotNull ... GenericTypes) {}
    default void addExtension(@NotNull Class<?> clazz) {}
    default void addExtension(@NotNull Class<?> clazz, String @NotNull ... GenericTypes) {}
    default void addMethod(Method method) {}
    default void addMethod(String method) {}
    default void addField(FieldBuilder field) {}
}
