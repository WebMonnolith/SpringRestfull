package org.restframework.web.core;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface GenericLambda <T, R> {
    R call(@NotNull T object);
}
