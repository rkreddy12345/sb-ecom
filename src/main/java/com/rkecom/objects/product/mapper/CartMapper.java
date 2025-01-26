package com.rkecom.objects.product.mapper;

import com.rkecom.db.entity.product.Cart;
import com.rkecom.ui.model.product.CartItemModel;
import com.rkecom.ui.model.product.CartModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class CartMapper {
    private final CartItemMapper cartItemMapper;
    public final Function < Cart, CartModel > toModel() {
        return cart -> CartModel.builder ()
                .cartId ( cart.getCartId () )
                .cartItems (toCartItemModelsList().apply ( cart ))
                .totalPrice ( cart.getTotalPrice () )
                .userEmail ( cart.getUser ().getEmail () )
                .build ();
    }

    private Function<Cart, List < CartItemModel > > toCartItemModelsList(){
        return cart -> cart.getCartItems ()
                .stream ()
                .map ( cartItem -> cartItemMapper.toCartItemModel ().apply ( cartItem ) )
                .toList ();
    }
}
