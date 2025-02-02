package com.rkecom.web.product.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItemModel {
    private Long cartItemId;
    private Long productId;
    private Integer quantity;
    private BigDecimal discount;
    private BigDecimal price;
    private Long cartId;
}
