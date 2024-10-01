package com.rkecom.ui.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rkecom.db.entity.Category;
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
    private String name;
    private String image;
    private String description;
    private Integer quantity;
    private Double price;
    private Double discount;
    private Double specialPrice;
    private Category category;
}
