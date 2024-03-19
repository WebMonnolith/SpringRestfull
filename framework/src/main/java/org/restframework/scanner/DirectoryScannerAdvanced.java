package org.restframework.scanner;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.restframework.web.annotations.markers.UpdateComponent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@RequiredArgsConstructor
public class DirectoryScannerAdvanced extends DirectoryScanner {

    private final Class<?> compilationComponentAnnotation;
    private final Class<?> updateComponentAnnotation;

    @Override
    public void scanDirectory(
            @NotNull File directory,
            Map<String, List<FileRecord>> packageMap,
            String packageName
    ) throws PackageScannerException {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    String newPackageName = packageName.isEmpty() ? file.getName() : packageName + "." + file.getName();
                    scanDirectory(file, packageMap, newPackageName);
                } else if (file.getName().endsWith(".java")) {
                    try {
                        List<String> lines = Files.readAllLines(file.toPath());
                        boolean containsCompilationComponent = containsAnnotation(lines, compilationComponentAnnotation);
                        boolean containsUpdateComponent = containsAnnotation(lines, updateComponentAnnotation);

                        if (containsCompilationComponent) {
                            String filePackage = packageName.isEmpty() ? "default" : packageName;
                            List<FileRecord> fileList = packageMap.getOrDefault(filePackage, new ArrayList<>());
                            FileRecord fileRecord = new FileRecord(file.getName(), file.length());
                            fileRecord.set(containsUpdateComponent); // Set true if @UpdateComponent annotation is present
                            fileList.add(fileRecord);
                            packageMap.put(filePackage, fileList);
                        }
                    } catch (IOException e) {
                        throw new PackageScannerException("Something went wrong while scanning your files!");
                    }
                }
            }
        }
    }

    private boolean containsAnnotation(@NotNull List<String> lines, Class<?> annotation) {
        String annotationSimpleName = annotation.getSimpleName();
        for (String line : lines) {
            if (line.contains("@" + annotationSimpleName)) {
                return true;
            }
        }
        return false;
    }
}

