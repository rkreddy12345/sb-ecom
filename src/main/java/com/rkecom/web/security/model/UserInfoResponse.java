package com.rkecom.web.security.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserInfoResponse {
    private Long id;
    private String token;
    private String username;
    private List <String> roles;
}
