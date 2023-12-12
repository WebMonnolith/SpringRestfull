package org.restframework.web.core.generators.builders;

import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public interface Builder <BuildType> {
    default BuildType build() { return null; }
    default BuildType build(@NotNull String filePath, @NotNull String dir) { return null; }
}
