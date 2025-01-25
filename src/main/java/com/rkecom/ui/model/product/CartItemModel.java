package com.rkecom.ui.model.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItemModel {
    private Long id;
    private Long productId;
    private Integer quantity;
    private Double discount;
    private Double price;
    private Long cartId;
}
