package com.rkecom.exception.handler;

import com.rkecom.config.AppConstants;
import com.rkecom.exception.APIException;
import com.rkecom.exception.ResourceNotFoundException;
import com.rkecom.payload.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity< ErrorResponse > handleInvalidInputs( MethodArgumentNotValidException ex) {
        Map <String, String> errors = new HashMap <> ();
        ex.getBindingResult ().getFieldErrors ().forEach (
                fieldError -> errors.put( fieldError.getField(), fieldError.getDefaultMessage ())
        );
        ex.getBindingResult ().getGlobalErrors ().forEach (
                globalError->errors.put ( globalError.getObjectName (), globalError.getDefaultMessage () )
        );

       return ResponseEntity.status ( HttpStatus.BAD_REQUEST ).body ( ErrorResponse.builder ()
               .error ( ErrorResponse.ErrorDetails.builder ()
                       .details ( errors )
                       .message ( AppConstants.INVALID_INPUT )
                       .build ())
               .status ( AppConstants.ERROR )
               .timestamp ( LocalDateTime.now () )
               .build ());
    }

    @ExceptionHandler( ResourceNotFoundException.class)
    public ResponseEntity< ErrorResponse > handleResourceNotFound( ResourceNotFoundException ex){
        ErrorResponse errorResponse= ErrorResponse.builder ()
                .error ( ErrorResponse.ErrorDetails.builder ().message ( ex.getMessage () ).build () )
                .status (ex.getHttpStatus ().getReasonPhrase ())
                .timestamp ( ex.getTimestamp () )
                .build ();
        return ResponseEntity.status ( ex.getHttpStatus () ).body ( errorResponse );
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity< ErrorResponse > handleAPIException ( APIException ex){
        ErrorResponse errorResponse= ErrorResponse.builder ()
                .error ( ErrorResponse.ErrorDetails.builder ().message ( ex.getMessage () ).build ())
                .status ( ex.getHttpStatus ().getReasonPhrase () )
                .timestamp ( ex.getTimestamp () )
                .build ();
        return ResponseEntity.status ( ex.getHttpStatus () ).body ( errorResponse );
    }

}
