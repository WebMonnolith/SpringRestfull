package org.restframework.security;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Getter
public abstract class Hashing<T extends String> {

    protected T input;
    protected final MessageDigest messageDigest;
    protected final byte[] message;

    public Hashing(@NotNull T input) throws NoSuchAlgorithmException {
        this.input = input;
        this.messageDigest = MessageDigest.getInstance("MD5");
        this.message = this.messageDigest.digest(input.getBytes(StandardCharsets.UTF_8));
    }
}
