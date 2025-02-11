package com.rkecom.core.exception.handler;

import com.rkecom.core.exception.ApiException;
import com.rkecom.core.response.ErrorResponse;
import com.rkecom.core.response.util.ErrorResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(2)
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler( ApiException.class)
    public ResponseEntity< ErrorResponse > handleAPIException ( ApiException ex){
        HttpStatus status = ex.getHttpStatus();

        if (status.is4xxClientError()) {
            LOGGER.warn("APIException: {} - {}", status, ex.getMessage());
        } else if (status.is5xxServerError()) {
            LOGGER.error("Critical APIException: {} - {}", status, ex.getMessage(), ex);
        }
        ErrorResponse errorResponse = ErrorResponseUtil.buildErrorResponse (
                status.getReasonPhrase (),
                ex.getMessage (),
                status );
        return ResponseEntity.status ( ex.getHttpStatus () ).body ( errorResponse );
    }

    @ExceptionHandler( Exception.class)
    public ResponseEntity<ErrorResponse> handleException ( Exception ex ){
        LOGGER.error("Unhandled exception occurred: {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = ErrorResponseUtil.buildErrorResponse (
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase (),
                "An unexpected error occurred. Please contact support.",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
        return ResponseEntity.status ( HttpStatus.INTERNAL_SERVER_ERROR ).body ( errorResponse );
    }

}
