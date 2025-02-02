package com.rkecom.web.product.controller;

import com.rkecom.core.controller.BaseController;
import com.rkecom.crud.product.service.CartService;
import com.rkecom.security.auth.util.UserDetailsUtil;
import com.rkecom.web.product.constants.CartConstants;
import com.rkecom.web.product.model.CartModel;
import com.rkecom.web.user.model.UserModel;
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

    @PostMapping("/carts/products/{productId}")
    public ResponseEntity< CartModel > addProductToCart( @PathVariable Long productId) {
       return new ResponseEntity<> (
               cartService.addProductToCart ( productId ) , HttpStatus.CREATED
       );
    }

    @GetMapping("/carts/user/cart")
    public ResponseEntity< CartModel > getUserCart() {
        UserModel currentUser = userDetailsUtil.getCurrentUser()
                .orElseThrow(() -> new AccessDeniedException ("Access denied: User is not authenticated"));
        return new ResponseEntity <> ( cartService.getUserCart ( currentUser.getEmail () ) , HttpStatus.FOUND );
    }

    @PutMapping("/carts/products/{productId}/update-quantity/{operation}")
    public ResponseEntity< CartModel > updateProductQuantityInCart( @PathVariable Long productId, @PathVariable String operation ) {
        if(!CartConstants.OPERATION_INCREMENT.equalsIgnoreCase ( operation ) &&
                !CartConstants.OPERATION_DECREMENT.equalsIgnoreCase ( operation )){
            return ResponseEntity.badRequest ().build ();
        }
        int quantityChange=CartConstants.OPERATION_INCREMENT.equalsIgnoreCase ( operation ) ? 1 : -1;
        return new ResponseEntity <> ( cartService.updateProductQtyInCart ( productId, quantityChange ) , HttpStatus.OK );
    }

    @DeleteMapping("/carts/product/{productId}")
    public ResponseEntity< String > deleteProductFromCart( @PathVariable Long productId ) {
        return new ResponseEntity<> ( cartService.deleteProductFromCart ( productId ) , HttpStatus.OK );
    }
}
