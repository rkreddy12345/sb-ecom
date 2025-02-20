package com.rkecom.web.order.model;

import com.rkecom.web.product.model.ProductModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemModel {
    private Long orderItemId;
    private ProductModel product;
    private Integer quantity;
    private BigDecimal discount;
    private BigDecimal price;
}
