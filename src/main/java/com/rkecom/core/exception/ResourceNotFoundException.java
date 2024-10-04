package com.rkecom.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ResourceNotFoundException extends BaseException {

    private final String resource;
    private final String field;
    private final Object value;

    public ResourceNotFoundException(String resource, String field, Object value) {
        super(
                HttpStatus.NOT_FOUND,
                String.format("%s not found with %s: %s", resource, field, value),
                LocalDateTime.now ()
        );
        this.resource = resource;
        this.field = field;
        this.value = value;
    }
}
