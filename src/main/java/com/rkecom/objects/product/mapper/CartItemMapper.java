package com.rkecom.objects.product.mapper;

import com.rkecom.db.entity.product.CartItem;
import com.rkecom.ui.model.product.CartItemModel;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CartItemMapper {
    public final Function < CartItem, CartItemModel > toCartItemModel(){
        return cartItem -> CartItemModel.builder ()
                .cartItemId ( cartItem.getCartItemId () )
                .productId ( cartItem.getProduct ().getProductId () )
                .discount ( cartItem.getDiscount () )
                .price ( cartItem.getPrice () )
                .quantity ( cartItem.getQuantity () )
                .cartId ( cartItem.getCart ().getCartId () )
                .build ();
    }
}
