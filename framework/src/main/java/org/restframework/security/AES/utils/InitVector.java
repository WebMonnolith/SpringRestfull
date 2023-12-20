package org.restframework.security.AES.utils;

import lombok.Getter;

import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;

@Getter
public class InitVector {
    private final IvParameterSpec parameter;

    public InitVector() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        this.parameter = new IvParameterSpec(iv);
    }

}
