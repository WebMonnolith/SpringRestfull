package org.restframework.security.AES;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@SuppressWarnings("unused")
public class StringSecurity {
    public static @NotNull String encryptString(
            @NotNull Algorithm algorithm,
            @NotNull String input,
            SecretKey key, IvParameterSpec parameter)

        throws
            NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidAlgorithmParameterException,
            InvalidKeyException,
            BadPaddingException,
            IllegalBlockSizeException
    {
        Cipher cipher = Cipher.getInstance(algorithm.getValue());
        cipher.init(Cipher.ENCRYPT_MODE, key, parameter);
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.getEncoder()
                .encodeToString(cipherText);
    }

    @Contract("_, _, _, _ -> new")
    public static @NotNull String decryptString(
            @NotNull Algorithm algorithm,
            @NotNull String cipherText,
            SecretKey key, IvParameterSpec iv)

        throws
            NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidAlgorithmParameterException,
            InvalidKeyException,
            BadPaddingException,
            IllegalBlockSizeException
    {

        Cipher cipher = Cipher.getInstance(algorithm.getValue());
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] plainText = cipher.doFinal(Base64.getDecoder()
                .decode(cipherText));
        return new String(plainText);
    }

}
