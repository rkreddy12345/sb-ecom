package com.rkecom.web.user.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressModel implements Serializable {
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
    @Pattern (regexp = "\\d{6}", message = "Pincode must be a 6-digit number")
    private String pincode;
    @NotBlank(message = "country is required")
    private String country;
}
