package org.restframework.scanner;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;
import java.util.Map;

public abstract class DirectoryScanner {
    public abstract void scanDirectory(@NotNull File directory, Map<String, List<FileRecord>> packageMap, String packageName);
}
