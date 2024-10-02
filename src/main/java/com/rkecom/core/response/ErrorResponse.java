package com.rkecom.core.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@JsonInclude( value = JsonInclude.Include.NON_NULL )
public class ErrorResponse {
    @JsonProperty("status")
    private String status;

    @JsonProperty("error")
    private ErrorDetails error;

    @JsonProperty("timestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd','HH:mm:ss:SSS")
    private LocalDateTime timestamp;

    @Getter
    @Builder
    @JsonInclude( value = JsonInclude.Include.NON_NULL )
    public static class ErrorDetails {
        @JsonProperty("code")
        private String code;

        @JsonProperty("message")
        private String message;

        @JsonProperty("details")
        private Object details;
    }
}
