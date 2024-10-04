package com.rkecom.core.exception.handler;

import com.rkecom.core.response.ErrorResponse;
import com.rkecom.core.response.util.ErrorResponseUtil;
import com.rkecom.core.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ResourceExceptionHandler {
    @ExceptionHandler ( ResourceNotFoundException.class)
    public ResponseEntity < ErrorResponse > handleResourceNotFound( ResourceNotFoundException ex){
        ErrorResponse errorResponse = ErrorResponseUtil.buildErrorResponse ( ex.getHttpStatus ().getReasonPhrase (), ex.getMessage (), ex.getHttpStatus () );
        return ResponseEntity.status ( ex.getHttpStatus () ).body ( errorResponse );
    }
}
