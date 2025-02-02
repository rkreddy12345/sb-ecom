package com.rkecom.crud.product.service;

import com.rkecom.crud.core.service.BaseService;
import com.rkecom.web.product.model.CartModel;

public interface CartService extends BaseService {
    CartModel addProductToCart(Long productId);
    CartModel updateProductQtyInCart(Long productId, Integer quantity);
    CartModel getUserCart(String email);
}
