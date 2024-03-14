package org.restframework.web.core.generics;

import lombok.Getter;

@Getter
public enum Generic {
    UUID("UUID"),
    LONG("Long");

    private final String value;

    Generic(String value) {
        this.value = value;
    }
}
