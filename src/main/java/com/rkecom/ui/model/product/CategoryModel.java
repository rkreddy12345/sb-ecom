package com.rkecom.ui.model.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude ( JsonInclude.Include.NON_NULL )
@Builder
public class CategoryModel {
    private Long categoryId;
    @NotBlank(message = "category name is required")
    @Size(min = 3, max = 10, message = "category name should be between 3 and 20 characters")
    private String name;
}
