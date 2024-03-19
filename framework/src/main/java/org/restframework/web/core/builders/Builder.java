package org.restframework.web.core.builders;

import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public interface Builder {
    default void build(@NotNull String filePath, @NotNull String dir) { }
}
