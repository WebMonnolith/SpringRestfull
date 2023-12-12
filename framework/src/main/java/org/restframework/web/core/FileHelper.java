package org.restframework.web.core;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class FileHelper {

    private static final String TARGET_FOLDER = "/target/classes/";
    public static final String NO_DIR = "";

    public static @NotNull String constructPath(
            @NotNull Class<?> clazz,
            @NotNull String srcRoot,
            @NotNull String basePackage)
    {
        URL location = clazz.getProtectionDomain().getCodeSource().getLocation();
        String classLocation = location.getPath().substring(1);

        int index = classLocation.indexOf(FileHelper.TARGET_FOLDER);
        if (index > -1) {
            String sourcePath = classLocation.substring(0, index) + srcRoot;
            Path currentPath = Paths.get(sourcePath).toAbsolutePath().normalize();
            String absolutePath = currentPath.toString();

            return String.format("%s\\%s", absolutePath, basePackage);
        }
        throw new IllegalArgumentException("Source path not found in the expected structure!");
    }

    public static String convertPackageToPath(String packageName) {
        return packageName.replace('.', '\\');
    }
}
