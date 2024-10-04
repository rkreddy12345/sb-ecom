package com.rkecom.core.exception;

import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.time.LocalDateTime;

public class ApiException extends BaseException {
    @Serial
    private static final long serialVersionUID = 1L;

    public ApiException ( String message ) {
        super(HttpStatus.BAD_REQUEST, message, LocalDateTime.now ());
    }
}
