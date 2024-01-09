package org.restframework.security.hashing;

public class RestSecurityException extends RuntimeException{

    public RestSecurityException(String message) {
        super(message);
    }
    public RestSecurityException(Throwable e) {
        super(e);
    }

}
