package com.rkecom.ui.model.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartModel {
    private Long cartId;
    private List <CartItemModel> cartItems = new ArrayList <> ();
    private String userEmail;
    private Double totalPrice;
}
