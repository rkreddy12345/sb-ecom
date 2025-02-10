package com.rkecom.web.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserModelWithoutRoles implements Serializable {
    private Long userId;
    private String userName;
    private String email;
    private List <AddressModel> addresses;
}

