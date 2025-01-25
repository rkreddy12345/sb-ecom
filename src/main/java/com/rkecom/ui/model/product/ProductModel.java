package com.rkecom.ui.model.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude( value = JsonInclude.Include.NON_NULL )
public class ProductModel {
    private Long id;
    @NotBlank (message = "Product name is required")
    @Size (min = 3, max = 10, message = "product name should be between 3 and 10 characters")
    private String name;
    private String image;
    @NotBlank (message = "product description is required")
    @Size (min = 5, max = 20, message = "product description should be between 3 and 20 characters")
    private String description;
    private Integer quantity;
    private Double price;
    private Double discount;
    private Double specialPrice;
    private Long categoryId;
}
