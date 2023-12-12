package org.restframework.web.core;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface AppRunner <T extends RestApp> {
    T call(@NotNull Class<?> clazz);
}
