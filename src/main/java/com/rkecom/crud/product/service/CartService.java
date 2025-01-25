package com.rkecom.crud.product.service;

import com.rkecom.crud.core.service.BaseService;
import com.rkecom.ui.model.product.CartModel;

public interface CartService extends BaseService {
    CartModel addProductToCart(Long productId, Integer quantity);
}
