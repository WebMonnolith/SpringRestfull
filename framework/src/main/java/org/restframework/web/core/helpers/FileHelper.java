package org.restframework.web.core.helpers;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileHelper {

    private static final String TARGET_FOLDER = "\\target\\classes";
    public static final String NO_DIR = "";

    public static @NotNull String constructPath(
            @NotNull Class<?> clazz,
            @NotNull String srcRoot,
            @NotNull String basePackage
    ) throws UnsupportedEncodingException, IllegalArgumentException {
        //TODO Add exception handling
        URL location = clazz.getProtectionDomain().getCodeSource().getLocation();
        String decodedPath = URLDecoder.decode(location.getPath(), StandardCharsets.UTF_8);
        if (decodedPath.startsWith("/"))
            decodedPath = decodedPath.substring(1);
        Path classPath = Paths.get(decodedPath, basePackage);

        int index = classPath.toString().indexOf(FileHelper.TARGET_FOLDER);
        if (index > -1) {
            String sourcePath = classPath.toString().substring(0, index) + srcRoot;
            Path currentPath = Paths.get(sourcePath).toAbsolutePath().normalize();
            String absolutePath = currentPath.toString();
            return Paths.get(absolutePath, basePackage).toString();
        }
        throw new IllegalArgumentException("Source path not found in the expected structure! - %s".formatted(classPath.toString()));
    }

    public static boolean fileExists(@NotNull File file) {
        return file.exists() && !file.isDirectory();
    }

    @Contract(pure = true)
    public static @NotNull String convertPackageToPath(@NotNull String packageName) {
        return packageName.replace('.', '\\');
    }
}
