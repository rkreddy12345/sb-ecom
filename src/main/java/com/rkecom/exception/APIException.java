package com.rkecom.exception;

import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.time.LocalDateTime;

public class APIException extends BaseException {
    @Serial
    private static final long serialVersionUID = 1L;

    public APIException( String message ) {
        super(HttpStatus.BAD_REQUEST, message, LocalDateTime.now ());
    }
}
