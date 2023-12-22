package org.restframework.security.AES;

import lombok.Getter;

@Getter
public enum Algorithm {
    PKCS5PADDING("\"AES/CBC/PKCS5Padding\"");

    private final String value;

    Algorithm(String value) {
        this.value = value;
    }
}
