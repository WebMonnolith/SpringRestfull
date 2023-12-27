package org.restframework.security.AES.core;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public interface Encryptor {
    void encrypt(CryptoAlgorithm algorithm, SecretKey key, IvParameterSpec iv) throws Exception;
    void decrypt(CryptoAlgorithm algorithm, SecretKey key, IvParameterSpec iv) throws Exception;
}
