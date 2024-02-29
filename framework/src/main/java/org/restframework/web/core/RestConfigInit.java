package org.restframework.web.core;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.restframework.web.annotations.EnableRestConfiguration;

public class RestConfigInit {

    public static final String CONTENT_ROOT_CONFIG_ID = "content-root";
    public static final String MODEL_COMPONENT_CONFIG_ID = "model-generation";
    public static final String DTO_COMPONENT_CONFIG_ID = "dto-generation";
    public static final String CUSTOM_GENERATION_CONFIG_ID = "custom-generation-strategy";

    public static @Nullable RestAppConfigurationContext configure(@NotNull Class<?> clazz) {
        EnableRestConfiguration configuration = clazz.getAnnotation(EnableRestConfiguration.class);
        if (hasConfiguration(clazz)) {
            return new RestAppConfigurationContext()
                    .configure(CONTENT_ROOT_CONFIG_ID, configuration.contentRoot())
                    .configure(MODEL_COMPONENT_CONFIG_ID, configuration.modelComponent())
                    .configure(DTO_COMPONENT_CONFIG_ID, configuration.dtoComponent())
                    .configure(CUSTOM_GENERATION_CONFIG_ID, configuration.useCustomGenerationStrategy());
        }

        return null;
    }

    public static boolean hasConfiguration(@NotNull Class<?> clazz) {
        return clazz.getAnnotation(EnableRestConfiguration.class) != null;
    }
}
