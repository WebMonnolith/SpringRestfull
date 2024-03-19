package org.restframework.scanner;

@SuppressWarnings("unused")
public class PackageScannerException extends RuntimeException {
    public PackageScannerException() {
        super();
    }

    public PackageScannerException(String message) {
        super(message);
    }

    public PackageScannerException(String message, Throwable cause) {
        super(message, cause);
    }
}
