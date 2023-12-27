package org.restframework.security;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

@Getter
@SuppressWarnings("unused")
public class MD5Hash <T extends String> extends Hashing<T> {

    private final BigInteger signum;

    public MD5Hash(@NotNull T input) throws NoSuchAlgorithmException {
        super(input);
        this.signum = new BigInteger(1, this.message);
    }

    public synchronized String convertMsgToHex() {
        String hashText = this.signum.toString(16);
        while (hashText.length() < 32)
            hashText = "0" + hashText;

        return hashText;
    }
}
