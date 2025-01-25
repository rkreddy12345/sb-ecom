package com.rkecom.objects.product.mapper;

import com.rkecom.db.entity.product.CartItem;
import com.rkecom.ui.model.product.CartItemModel;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CartItemMapper {
    public final Function < CartItem, CartItemModel > toCartItemModel(){
        return cartItem -> CartItemModel.builder ()
                .id ( cartItem.getId () )
                .cartId ( cartItem.getId () )
                .productId ( cartItem.getProduct ().getId () )
                .quantity ( cartItem.getQuantity () )
                .discount ( cartItem.getDiscount () )
                .price ( cartItem.getPrice () )
                .build ();
    }
}
