package com.rkecom.security.ui.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class SignupRequest {
    @NotBlank(message = "username is required")
    @Size (min = 8, max = 15, message = "user name should have min 8 and max 15 characters")
    private String username;
    @NotBlank(message = "password is required")
    private String password;
    @NotBlank(message = "email is required")
    @Email(message = "invalid email")
    private String email;
    private List <String> roles;
}
