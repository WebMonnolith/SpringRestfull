package org.restframework.web.exceptions;

public class RestException extends RuntimeException {
    public RestException(String message) {
        super(message);
    }
    public RestException(String message, Throwable e) {
        super(message, e);
    }
}
