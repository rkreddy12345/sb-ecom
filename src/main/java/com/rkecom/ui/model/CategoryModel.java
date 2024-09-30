package com.rkecom.ui.model;

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
    private Long id;
    @NotBlank(message = "Category name is required")
    @Size(min = 3, max = 20, message = "Category name should be between 3 and 20 characters")
    private String name;
}
