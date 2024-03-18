package org.restframework.scanner;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class DirectoryScannerAdvanced extends DirectoryScanner {

    private final Class<?> annotationName;

    @Override
    public void scanDirectory(@NotNull File directory, Map<String, List<FileRecord>> packageMap, String packageName) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    String newPackageName = packageName.isEmpty() ? file.getName() : packageName + "." + file.getName();
                    scanDirectory(file, packageMap, newPackageName);
                } else if (file.getName().endsWith(".java")) {
                    try {
                        List<String> lines = Files.readAllLines(file.toPath());
                        for (String line : lines) {
                            if (line.contains("@"+this.annotationName.getSimpleName())) {
                                String filePackage = packageName.isEmpty() ? "default" : packageName;
                                List<FileRecord> fileList = packageMap.getOrDefault(filePackage, new ArrayList<>());
                                fileList.add(new FileRecord(file.getAbsolutePath(), file.length()));
                                packageMap.put(filePackage, fileList);
                                break;
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
