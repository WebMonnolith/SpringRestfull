package org.restframework.security.hashing;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.restframework.security.RestSecurityException;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Getter
@SuppressWarnings("unused")
public class MD5Hash <T extends String> {

    private final MessageDigest messageDigest;
    private final byte[] message;
    private final BigInteger signum;

    public MD5Hash(@NotNull T input) {
        try {
            this.messageDigest = MessageDigest.getInstance("MD5");
            this.message = this.messageDigest.digest(input.getBytes());
            this.signum = new BigInteger(1, this.message);
        }
        catch (NoSuchAlgorithmException e) {
            throw new RestSecurityException(e);
        }

    }

    public synchronized String convertMsgToHex() {
        StringBuilder hashText = new StringBuilder(this.signum.toString(16));
        while (hashText.length() < 32)
            hashText.insert(0, "0");

        return hashText.toString();
    }
}
