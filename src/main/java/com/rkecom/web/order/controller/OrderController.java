package com.rkecom.web.order.controller;

import com.rkecom.core.controller.BaseController;
import com.rkecom.crud.order.service.OrderService;
import com.rkecom.security.auth.util.UserDetailsUtil;
import com.rkecom.web.order.model.OrderModel;
import com.rkecom.web.order.model.OrderRequest;
import com.rkecom.web.user.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/api/v1")
@RequiredArgsConstructor
public class OrderController extends BaseController {
    private final OrderService orderService;
    private final UserDetailsUtil userDetailsUtil;

    @PostMapping("/order/users/payments/{paymentType}")
    public ResponseEntity< OrderModel > order(@RequestBody OrderRequest orderRequest, @PathVariable String paymentType) {
        UserModel user = userDetailsUtil.getCurrentUser ().orElseThrow (
                ()->new AccessDeniedException ( "Access denied: User is not authenticated" )
        );
        OrderModel orderModel=orderService.placeOrder (user.getEmail (), orderRequest, paymentType);
        return new ResponseEntity <> ( orderModel, HttpStatus.CREATED );
    }
}
