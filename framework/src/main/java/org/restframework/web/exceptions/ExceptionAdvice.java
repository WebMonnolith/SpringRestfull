package org.restframework.web.exceptions;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;

@SuppressWarnings("unused")
@ControllerAdvice
public interface ExceptionAdvice {
    @ExceptionHandler(NullPointerException.class)
    default ResponseEntity<ErrorResponse> handleNullPointerException(@NotNull Exception e) {
        HttpStatus status = HttpStatus.NOT_FOUND; // 404
        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        e.getMessage()),
                status);
    }
    @ExceptionHandler(Exception.class)
    default ResponseEntity<ErrorResponse> handleExceptions(@NotNull Exception e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        String stackTrace = stringWriter.toString();
        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        e.getMessage(),
                        stackTrace),
                status);
    }
    @ExceptionHandler(DataNotFoundException.class)
    default ResponseEntity<ErrorResponse> handleDataNotFoundException(@NotNull Exception e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        String stackTrace = stringWriter.toString();
        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        e.getMessage(),
                        stackTrace),
                status);
    }
    @ExceptionHandler(RestException.class)
    default ResponseEntity<ErrorResponse> handleRestException(@NotNull Exception e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        String stackTrace = stringWriter.toString();
        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        e.getMessage(),
                        stackTrace),
                status);
    }
}
