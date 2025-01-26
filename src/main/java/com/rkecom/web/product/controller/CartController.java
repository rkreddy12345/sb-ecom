package com.rkecom.web.product.controller;

import com.rkecom.core.controller.BaseController;
import com.rkecom.crud.product.service.CartService;
import com.rkecom.security.auth.util.UserDetailsUtil;
import com.rkecom.ui.model.product.CartModel;
import com.rkecom.ui.model.user.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CartController extends BaseController {
    private final CartService cartService;
    private final UserDetailsUtil userDetailsUtil;

    @PostMapping("/carts/products/{productId}/quantity/{quantity}")
    public ResponseEntity< CartModel > addProductToCart( @PathVariable Long productId, @PathVariable Integer quantity ) {
       return new ResponseEntity<> (
               cartService.addProductToCart( productId, quantity ) , HttpStatus.CREATED
       );
    }

    @GetMapping("/carts/user/cart")
    public ResponseEntity< CartModel > getUserCart() {
        UserModel currentUser = userDetailsUtil.getCurrentUser()
                .orElseThrow(() -> new AccessDeniedException ("Access denied: User is not authenticated"));
        return new ResponseEntity <> ( cartService.getUserCart ( currentUser.getEmail () ) , HttpStatus.FOUND );
    }
}
