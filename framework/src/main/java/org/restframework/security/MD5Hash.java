package org.restframework.security;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Getter
@SuppressWarnings("unused")
public class MD5Hash <T extends String> {

    private final MessageDigest messageDigest;
    private final byte[] message;
    private final BigInteger signum;

    public MD5Hash(@NotNull T input) throws NoSuchAlgorithmException {
        this.messageDigest = MessageDigest.getInstance("MD5");
        this.message = this.messageDigest.digest(input.getBytes());
        this.signum = new BigInteger(1, this.message);

    }

    public synchronized String convertMsgToHex() {
        String hashText = this.signum.toString(16);
        while (hashText.length() < 32)
            hashText = "0" + hashText;

        return hashText;
    }
}
