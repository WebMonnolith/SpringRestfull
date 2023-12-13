package org.restframework.web.core.generators.builders;

import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public interface Builder {
    default void build() { }
    default void build(@NotNull String filePath, @NotNull String dir) { }
}
