package org.restframework.security;

public class RestSecurityException extends RuntimeException{

    public RestSecurityException(String message) {
        super(message);
    }
    public RestSecurityException(Throwable e) {
        super(e);
    }

}
