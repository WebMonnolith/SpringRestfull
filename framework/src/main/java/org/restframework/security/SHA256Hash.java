package org.restframework.security;

import org.jetbrains.annotations.NotNull;

import java.security.NoSuchAlgorithmException;

@SuppressWarnings("unused")
public class SHA256Hash <T extends String> extends Hashing<T> {

    public SHA256Hash(@NotNull T input) throws NoSuchAlgorithmException {
        super(input);
    }

    public synchronized @NotNull String bytesToHex(byte @NotNull [] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xFF & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }


}
