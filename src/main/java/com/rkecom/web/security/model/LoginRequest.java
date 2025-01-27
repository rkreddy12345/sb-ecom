package com.rkecom.web.security.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank( message = "user name is required" )
    private String username;
    @NotBlank(message = "password is required")
    private String password;
}
