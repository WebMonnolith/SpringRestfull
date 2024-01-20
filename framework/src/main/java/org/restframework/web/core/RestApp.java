package org.restframework.web.core;

import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public interface RestApp {
    <T> void run(@NotNull Class<T> clazz);
    default <T> void run(@NotNull AppRunner<RestApp> runnable) {}
    default <T> void run(String[] args) {}
    default <T> void run(String[] args, @NotNull AppRunner<RestApp> runnable) {}
}
