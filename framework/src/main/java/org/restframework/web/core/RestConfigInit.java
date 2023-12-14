package org.restframework.web.core;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.restframework.web.annotations.EnableRestConfiguration;

public class RestConfigInit {
    public static @Nullable RestAppConfigurationContext configure(@NotNull Class<?> clazz) {
        EnableRestConfiguration configuration = clazz.getAnnotation(EnableRestConfiguration.class);
        if (hasConfiguration(clazz)) {
            return new RestAppConfigurationContext()
                    .configure("content-root", configuration.contentRoot());
        }

        return null;
    }

    public static boolean hasConfiguration(@NotNull Class<?> clazz) {
        return clazz.getAnnotation(EnableRestConfiguration.class) != null;
    }
}
