package org.restframework.scanner;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DirectoryScannerSimple extends DirectoryScanner {
    @Override
    public void scanDirectory(@NotNull File directory, Map<String, List<FileRecord>> packageMap, String packageName) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    String newPackageName = packageName.isEmpty() ? file.getName() : packageName + "." + file.getName();
                    scanDirectory(file, packageMap, newPackageName);
                } else {
                    String filePackage = packageName.isEmpty() ? "default" : packageName;
                    List<FileRecord> fileList = packageMap.getOrDefault(filePackage, new ArrayList<>());
                    fileList.add(new FileRecord(file.getAbsolutePath(), file.length()));
                    packageMap.put(filePackage, fileList);
                }
            }
        }
    }
}
