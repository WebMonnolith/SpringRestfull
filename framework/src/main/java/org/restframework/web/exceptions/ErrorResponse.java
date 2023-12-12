package org.restframework.web.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import java.util.Date;

@Getter
@SuppressWarnings("unused")
public final class ErrorResponse {
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    @Setter private Date timestamp;
    @Setter private int code;
    @Setter private String status;
    @Setter private String message;
    @Setter private String stackTrace;
    @Setter private Object data;

    public ErrorResponse() {
        timestamp = new Date();
    }

    public ErrorResponse(@NotNull HttpStatus httpStatus, String message) {
        this();

        this.code = httpStatus.value();
        this.status = httpStatus.name();
        this.message = message;
    }

    public ErrorResponse(
            @NotNull HttpStatus httpStatus,
            String message,
            String stackTrace
    ) {
        this(httpStatus, message);
        this.stackTrace = stackTrace;
    }

    public ErrorResponse(
            @NotNull HttpStatus httpStatus,
            String message,
            String stackTrace,
            Object data
    ) {
        this(httpStatus, message, stackTrace);
        this.data = data;
    }
}