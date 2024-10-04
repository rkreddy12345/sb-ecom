package com.rkecom.core.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class BaseException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    private HttpStatus httpStatus;
    private String message;
    private LocalDateTime timestamp;

    public BaseException(HttpStatus httpStatus, String message, LocalDateTime timestamp) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message;
        this.timestamp = timestamp;
    }

    public BaseException ( HttpStatus httpStatus, LocalDateTime now ) {
        this.httpStatus = httpStatus;
        this.timestamp = now;
    }

    public BaseException ( String message ) {
        super(message);
    }
}
