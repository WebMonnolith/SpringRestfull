package org.restframework.web.core;

import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public interface RestApp<T extends RestApp<T>> {
    <ClazzType> T run(@NotNull Class<ClazzType> clazz);
    T run(@NotNull AppRunner<RestApp<T>> runnable);
    T run(String[] args);
    T run(String[] args, @NotNull AppRunner<RestApp<T>> runnable);
}
