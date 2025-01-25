package com.rkecom.web.product.controller;

import com.rkecom.core.controller.BaseController;
import com.rkecom.crud.product.service.CartService;
import com.rkecom.ui.model.product.CartModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CartController extends BaseController {
    private final CartService cartService;
    @PostMapping("/carts/products/{productId}/quantity/{quantity}")
    public ResponseEntity< CartModel > addProductToCart( @PathVariable Long productId, @PathVariable Integer quantity ) {
       return new ResponseEntity<> (
               cartService.addProductToCart( productId, quantity ) , HttpStatus.CREATED
       );
    }
}
