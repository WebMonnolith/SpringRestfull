package org.restframework.web.core;

import lombok.Getter;

@Getter
public enum Modifier {
    PUBLIC("public"),
    PRIVATE("private"),
    PROTECTED("protected"),
    PUBLIC_FINAL("public final"),
    PRIVATE_FINAL("private final"),
    PROTECTED_FINAL("protected final");

    private final String value;

    Modifier(String value) {
        this.value = value;
    }
}
