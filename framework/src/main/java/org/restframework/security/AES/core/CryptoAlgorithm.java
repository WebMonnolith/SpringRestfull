package org.restframework.security.AES.core;

import lombok.Getter;

@Getter
public enum CryptoAlgorithm {
    AES_CBC_PKCS5_PADDING("AES/CBC/PKCS5Padding"),
    AES_ECB_PKCS5_PADDING("AES/ECB/PKCS5Padding"),
    AES_GCM_NO_PADDING("AES/GCM/NoPadding"),
    DES_CBC_PKCS5_PADDING("DES/CBC/PKCS5Padding"),
    DES_ECB_PKCS5_PADDING("DES/ECB/PKCS5Padding"),
    RSA_ECB_PKCS1_PADDING("RSA/ECB/PKCS1Padding"),
    RSA_ECB_OAEP_PADDING("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");

    private final String value;

    CryptoAlgorithm(String value) {
        this.value = value;
    }
}
