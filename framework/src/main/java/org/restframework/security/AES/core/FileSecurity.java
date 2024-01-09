package org.restframework.security.AES.core;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

import javax.crypto.*;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@EqualsAndHashCode()
@Data
@SuppressWarnings("unused")
public class FileSecurity implements Encryptor {

    private File inputFile;
    private File outputFile;

    public static final String ENCRYPTED_FILE_EXTENSION = ".encrypted";
    public static final String DECRYPTED_FILE_EXTENSION = ".decrypted";

    @Override
    public void encrypt(CryptoAlgorithm algorithm, SecretKey key, IvParameterSpec iv) throws Exception {
        FileSecurity.encryptFile(algorithm, key, iv, this.inputFile, this.outputFile);
    }

    @Override
    public void decrypt(CryptoAlgorithm algorithm, SecretKey key, IvParameterSpec iv) throws Exception {
    }

    public static void encryptFile(
            @NotNull CryptoAlgorithm algorithm,
            @NotNull SecretKey key,
            @NotNull IvParameterSpec iv,
            File inputFile, File outputFile)

            throws
            IOException,
            NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidAlgorithmParameterException,
            InvalidKeyException,
            BadPaddingException,
            IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm.getValue());
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        FileInputStream inputStream = new FileInputStream(inputFile);
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        byte[] buffer = new byte[64];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            byte[] output = cipher.update(buffer, 0, bytesRead);
            if (output != null) {
                outputStream.write(output);
            }
        }
        byte[] outputBytes = cipher.doFinal();
        if (outputBytes != null) {
            outputStream.write(outputBytes);
        }
        inputStream.close();
        outputStream.close();
    }
}
