package org.restframework.web.exceptions;

import org.hibernate.exception.ConstraintViolationException;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.NoSuchElementException;

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

    @ExceptionHandler(ArithmeticException.class)
    default ResponseEntity<ErrorResponse> handleArithmeticException(@NotNull Exception e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        e.getMessage()),
                status);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    default ResponseEntity<ErrorResponse> handleIllegalArgumentException(@NotNull Exception e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        e.getMessage()),
                status);
    }

    @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    default ResponseEntity<ErrorResponse> handleArrayIndexOutOfBoundsException(@NotNull Exception e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        e.getMessage()),
                status);
    }

    @ExceptionHandler(AssertionError.class)
    default ResponseEntity<ErrorResponse> handleAssertionError(@NotNull Exception e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        e.getMessage()),
                status);
    }

    @ExceptionHandler(ClassCastException.class)
    default ResponseEntity<ErrorResponse> handleClassCastException(@NotNull Exception e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        e.getMessage()),
                status);
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    default ResponseEntity<ErrorResponse> handleUnsupportedOperationException(@NotNull Exception e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        e.getMessage()),
                status);
    }

    @ExceptionHandler(NoSuchElementException.class)
    default ResponseEntity<ErrorResponse> handleNoSuchElementException(@NotNull Exception e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        e.getMessage()),
                status);
    }

    @ExceptionHandler(SecurityException.class)
    default ResponseEntity<ErrorResponse> handleSecurityException(@NotNull Exception e) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        e.getMessage()),
                status);
    }

    @ExceptionHandler(IllegalStateException.class)
    default ResponseEntity<ErrorResponse> handleIllegalStateException(@NotNull Exception e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        e.getMessage()),
                status);
    }

    @ExceptionHandler(InterruptedException.class)
    default ResponseEntity<ErrorResponse> handleInterruptedException(@NotNull Exception e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        e.getMessage()),
                status);
    }

    @ExceptionHandler(UnsupportedClassVersionError.class)
    default ResponseEntity<ErrorResponse> handleUnsupportedClassVersionError(@NotNull Exception e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        e.getMessage()),
                status);
    }

    @ExceptionHandler(ClassNotFoundException.class)
    default ResponseEntity<ErrorResponse> handleClassNotFoundException(@NotNull Exception e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        e.getMessage()),
                status);
    }

    @ExceptionHandler(OutOfMemoryError.class)
    default ResponseEntity<ErrorResponse> handleOutOfMemoryError(@NotNull Exception e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        e.getMessage()),
                status);
    }

    @ExceptionHandler(NoSuchMethodError.class)
    default ResponseEntity<ErrorResponse> handleNoSuchMethodError(@NotNull Exception e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        e.getMessage()),
                status);
    }

    @ExceptionHandler(UnsupportedEncodingException.class)
    default ResponseEntity<ErrorResponse> handleUnsupportedEncodingException(@NotNull Exception e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        e.getMessage()),
                status);
    }

    @ExceptionHandler(IllegalAccessException.class)
    default ResponseEntity<ErrorResponse> handleIllegalAccessException(@NotNull Exception e) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        e.getMessage()),
                status);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    default ResponseEntity<ErrorResponse> handleConstraintViolationException(@NotNull ConstraintViolationException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        e.getMessage()),
                status);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    default ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(@NotNull DataIntegrityViolationException e) {
        HttpStatus status = HttpStatus.CONFLICT;
        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        e.getMessage()),
                status);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    default ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(@NotNull HttpMessageNotReadableException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        e.getMessage()),
                status);
    }
}
