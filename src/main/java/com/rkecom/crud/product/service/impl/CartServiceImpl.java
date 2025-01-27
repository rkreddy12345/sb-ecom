package com.rkecom.crud.product.service.impl;

import com.rkecom.core.exception.ApiException;
import com.rkecom.core.exception.ResourceNotFoundException;
import com.rkecom.core.util.ResourceConstants;
import com.rkecom.crud.product.service.CartService;
import com.rkecom.data.product.repository.CartItemRepository;
import com.rkecom.data.product.repository.CartRepository;
import com.rkecom.data.product.repository.ProductRepository;
import com.rkecom.db.entity.product.Cart;
import com.rkecom.db.entity.product.CartItem;
import com.rkecom.db.entity.product.Product;
import com.rkecom.objects.product.mapper.CartMapper;
import com.rkecom.objects.user.mapper.UserMapper;
import com.rkecom.security.auth.util.UserDetailsUtil;
import com.rkecom.web.product.model.CartModel;
import com.rkecom.web.user.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Transactional(readOnly = true)
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final UserDetailsUtil userDetailsUtil;
    private final UserMapper userMapper;
    private final CartItemRepository cartItemRepository;
    private final CartMapper cartMapper;
    private final ProductRepository productRepository;
    @Override
    @Transactional
    public CartModel addProductToCart ( Long productId, Integer quantity ) {
        Cart cart=getTheCart ();
        Product product=productRepository.findById ( productId ).orElseThrow (
                ()->new ResourceNotFoundException ( ResourceConstants.PRODUCT, "id", productId)
        );
        Optional<CartItem> cartItem=cartItemRepository.findByProductIdAndCartId ( productId, cart.getCartId () );
        if (cartItem.isPresent()) {
            throw new ApiException("The product is already in the cart. Please update the quantity instead.");
        }
        if (product.getQuantity() < quantity) {
            throw new ApiException("Insufficient stock available to add the requested quantity.");
        }
        if (product.getQuantity() == 0) {
            throw new ApiException("The product is out of stock.");
        }

        CartItem newCartItem=CartItem.builder ()
                .cart ( cart )
                .product ( product )
                .quantity ( quantity )
                .price ( product.getSpecialPrice () )
                .discount ( product.getDiscount () )
                .build ();
        cartItemRepository.save( newCartItem );
        product.setQuantity ( product.getQuantity () - quantity );
        cart.setTotalPrice ( cart.getTotalPrice ()+( product.getSpecialPrice () * quantity ) );
        cart.getCartItems ().add ( newCartItem );
        Cart savedCart=cartRepository.save( cart );
        return cartMapper.toModel ().apply ( savedCart );
    }

    @Override
    public CartModel getUserCart ( String email ) {
        Cart cart =  cartRepository.findCartByEmail ( email ).orElseThrow (
                ( ) -> new ResourceNotFoundException ( ResourceConstants.CART, "email", email )
        );

        return cartMapper.toModel ().apply ( cart );
    }

    private Cart getTheCart() {
        UserModel currentUser = userDetailsUtil.getCurrentUser()
                .orElseThrow(() -> new AccessDeniedException("Access denied: User is not authenticated"));

        return cartRepository.findCartByEmail(currentUser.getEmail())
                .orElseGet ( ()->createNewCart () );
    }

    private Cart createNewCart() {
        Cart cart = Cart.builder ()
                .user ( userMapper.toEntity ().apply ( userDetailsUtil.getCurrentUser ()
                        .orElseThrow (() -> new AccessDeniedException("Access denied: User is not authenticated"))) )
                .cartItems ( new ArrayList <> () )
                .totalPrice ( 0.0 )
                .build ();
        return cartRepository.save( cart );
    }

}
