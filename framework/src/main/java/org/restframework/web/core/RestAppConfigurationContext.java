package org.restframework.web.core;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@Data
@SuppressWarnings("unused")
@Slf4j
public class RestAppConfigurationContext {

    private static final Map<String, Object> configurations = new HashMap<>();

    public RestAppConfigurationContext() {

    }

    public <T> RestAppConfigurationContext configure(@NotNull String key, @NotNull T value) {
        this.insert(key, value);
        return this;
    }

    public <T> RestAppConfigurationContext reconfigure(@NotNull String key, @NotNull T value) {
        this.replace(key, value);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> T getValueByKey(@NotNull String key) {
        return (T) RestAppConfigurationContext.configurations.get(key);
    }

    private <T> void replace(@NotNull String key, @NotNull T value) {
        if (RestAppConfigurationContext.configurations.containsKey(key)) {
            log.warn("No such configuration available!");
            return;
        }

        RestAppConfigurationContext.configurations.remove(key);
        RestAppConfigurationContext.configurations.put(key, value);
        log.info("Replaced value: [{}], by key: [{}]", value, key);
    }

    private <T> void insert(@NotNull String key, @NotNull T value) {
        RestAppConfigurationContext.configurations.remove(key);
        RestAppConfigurationContext.configurations.put(key, value);
        log.info("Configured value: [{}], by key: [{}]", value, key);
    }
}
