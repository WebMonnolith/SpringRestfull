package org.restframework.security.AES;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@SuppressWarnings("unused")
public class ObjectSecurity {
    @Contract("_, _, _, _ -> new")
    public static @NotNull SealedObject encryptObject(
            @NotNull Algorithm algorithm,
            Serializable object,
            SecretKey key,
            IvParameterSpec iv)

            throws
            NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidAlgorithmParameterException,
            InvalidKeyException,
            IOException,
            IllegalBlockSizeException
    {

        Cipher cipher = Cipher.getInstance(algorithm.getValue());
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        return new SealedObject(object, cipher);
    }


    public static Serializable decryptObject(
            @NotNull Algorithm algorithm,
            @NotNull SealedObject sealedObject,
            SecretKey key,
            IvParameterSpec iv)

            throws
            NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidAlgorithmParameterException,
            InvalidKeyException,
            ClassNotFoundException,
            BadPaddingException,
            IllegalBlockSizeException,
            IOException
    {

        Cipher cipher = Cipher.getInstance(algorithm.getValue());
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        return (Serializable) sealedObject.getObject(cipher);
    }
}
