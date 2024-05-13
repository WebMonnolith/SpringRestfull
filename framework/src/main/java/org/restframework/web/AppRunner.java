package org.restframework.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;

@Slf4j
public class AppRunner {
    public static void safe(Class<?> clazz, String[] args) {
        try {
            new WebApp(clazz)
                    .run(args);
        } catch (Exception exc) {
            log.info("ERROR while running app, using spring boot way - %s".formatted(exc.getMessage()));
            SpringApplication.run(clazz, args);
        }
    }
}
