package com.rkecom.web.user.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressModel {
    private Long addressId;
    @NotBlank (message = "street is required")
    private String street;
    @NotBlank (message = "building is required")
    private String building;
    @NotBlank (message = "city is required")
    private String city;
    @NotBlank (message = "state is required")
    private String state;
    @NotBlank (message = "pincode is required")
    private String pincode;
    @NotBlank(message = "country is required")
    private String country;
    private String userName;
    private String email;
}
