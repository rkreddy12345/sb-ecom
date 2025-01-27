package com.rkecom.web.product.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItemModel {
    private Long cartItemId;
    private Long productId;
    private Integer quantity;
    private Double discount;
    private Double price;
    private Long cartId;
}
