package org.restframework.security.AES.core;

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
public class ObjectSecurity implements Encryptor {

    private Serializable object;
    private SealedObject sealedObject;

    public ObjectSecurity(Serializable object) {
        this.object = object;
    }

    @Override
    public void encrypt(CryptoAlgorithm algorithm, SecretKey key, IvParameterSpec iv) throws Exception {
        this.sealedObject = ObjectSecurity.encryptObject(algorithm, object, key, iv);
    }

    @Override
    public void decrypt(CryptoAlgorithm algorithm, SecretKey key, IvParameterSpec iv) throws Exception {
        if (this.sealedObject == null) return;
        this.object = ObjectSecurity.decryptObject(algorithm, this.sealedObject, key, iv);
    }

    @Contract("_, _, _, _ -> new")
    public static @NotNull SealedObject encryptObject(
            @NotNull CryptoAlgorithm algorithm,
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
            @NotNull CryptoAlgorithm algorithm,
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
