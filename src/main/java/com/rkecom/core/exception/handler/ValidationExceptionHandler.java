package com.rkecom.core.exception.handler;

import com.rkecom.core.response.ErrorResponse;
import com.rkecom.core.response.util.ErrorConstants;
import com.rkecom.core.response.util.ErrorResponseUtil;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ValidationExceptionHandler {
    @ExceptionHandler ( MethodArgumentNotValidException.class)
    public ResponseEntity < ErrorResponse > handleInvalidArguments( MethodArgumentNotValidException ex) {
        Map <String, String> errors = new HashMap <> ();
        ex.getBindingResult ().getFieldErrors ().forEach (
                fieldError -> errors.put( fieldError.getField(), fieldError.getDefaultMessage ())
        );
        ex.getBindingResult ().getGlobalErrors ().forEach (
                globalError->errors.put ( globalError.getObjectName (), globalError.getDefaultMessage () )
        );
        ErrorResponse errorResponse = ErrorResponseUtil.buildErrorResponse (    ErrorConstants.ERROR_STATUS, ErrorConstants.INVALID_INPUT_MSG, errors, HttpStatus.BAD_REQUEST );
        return ResponseEntity.status ( HttpStatus.BAD_REQUEST ).body ( errorResponse );
    }

    @ExceptionHandler( ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation( ConstraintViolationException ex) {
        Map <String, String> errors = ex.getConstraintViolations ().stream ().collect ( Collectors.toMap (
                violation -> violation.getPropertyPath().toString().split("\\.")[violation.getPropertyPath().toString().split("\\.").length - 1],
                ConstraintViolation::getMessage
        ) );
        ErrorResponse errorResponse = ErrorResponseUtil.buildErrorResponse (
                ErrorConstants.ERROR_STATUS, ErrorConstants.INVALID_INPUT_MSG,
                errors, HttpStatus.BAD_REQUEST
        );
        return ResponseEntity.status ( HttpStatus.BAD_REQUEST ).body ( errorResponse );
    }
}
