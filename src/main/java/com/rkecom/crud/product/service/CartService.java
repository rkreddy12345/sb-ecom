package com.rkecom.crud.product.service;

import com.rkecom.crud.core.service.BaseService;
import com.rkecom.web.product.model.CartModel;

public interface CartService extends BaseService {
    CartModel addProductToCart(Long productId);
    CartModel updateProductQtyInCart(Long productId, Integer quantity);
    CartModel getUserCart(String email);
    CartModel deleteProductFromCart(Long productId);
    void deleteProductFromCart(Long productId, Long cartId);
    void updateProductInCarts(Long cartId, Long productId);
}
