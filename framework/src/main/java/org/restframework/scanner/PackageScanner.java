package org.restframework.scanner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class PackageScanner {

    private final DirectoryScanner scanner;

    public @NotNull Map<String, List<FileRecord>> scanPackages(String rootPath) {
        Map<String, List<FileRecord>> packageMap = new HashMap<>();
        File root = new File(rootPath);
        if (!root.exists() || !root.isDirectory()) {
            log.warn("Invalid directory: " + rootPath);
            return packageMap;
        }
        scanner.scanDirectory(root, packageMap, "");
        return packageMap;
    }
}
