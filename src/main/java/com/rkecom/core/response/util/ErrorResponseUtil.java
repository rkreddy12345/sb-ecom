package com.rkecom.core.response.util;

import com.rkecom.core.response.ErrorResponse;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

public class ErrorResponseUtil {

    private ErrorResponseUtil() {
    }
    public static ErrorResponse buildErrorResponse(String status, String message, Map<String, String> details, HttpStatus httpStatus) {
        return ErrorResponse.builder()
                .status(status != null ? status : httpStatus.getReasonPhrase())
                .timestamp(LocalDateTime.now())
                .error(ErrorResponse.ErrorDetails.builder()
                        .message(message)
                        .details(details)
                        .build())
                .build();
    }

    public static ErrorResponse buildErrorResponse(String status, String message, Object details, HttpStatus httpStatus) {
        return ErrorResponse.builder()
                .status(status != null ? status : httpStatus.getReasonPhrase())
                .timestamp(LocalDateTime.now())
                .error(ErrorResponse.ErrorDetails.builder()
                        .message(message)
                        .details(details)
                        .build())
                .build();
    }

    public static ErrorResponse buildErrorResponse(String status, String message, HttpStatus httpStatus) {
        return ErrorResponse.builder()
                .status(status != null ? status : httpStatus.getReasonPhrase())
                .timestamp(LocalDateTime.now())
                .error(ErrorResponse.ErrorDetails.builder()
                        .message(message)
                        .build())
                .build();
    }
    
    public static ErrorResponse buildErrorResponse(String message, HttpStatus httpStatus) {
        return ErrorResponse.builder()
                .status(httpStatus.getReasonPhrase())
                .timestamp(LocalDateTime.now())
                .error(ErrorResponse.ErrorDetails.builder()
                        .message(message)
                        .build())
                .build();
    }
}
