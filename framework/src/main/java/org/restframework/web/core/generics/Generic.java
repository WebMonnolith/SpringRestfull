package org.restframework.web.core.generics;

import lombok.Getter;

@Getter
public enum Generic {
    UUID("UUID"),
    INTEGER("Integer"),
    LONG("Long");

    private final String value;

    Generic(String value) {
        this.value = value;
    }
}
