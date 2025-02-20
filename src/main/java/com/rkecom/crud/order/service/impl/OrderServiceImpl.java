package com.rkecom.crud.order.service.impl;

import com.rkecom.core.exception.ApiException;
import com.rkecom.core.exception.ResourceNotFoundException;
import com.rkecom.core.util.ResourceConstants;
import com.rkecom.crud.order.service.OrderService;
import com.rkecom.crud.product.service.CartService;
import com.rkecom.data.order.repository.OrderRepository;
import com.rkecom.data.product.repository.CartRepository;
import com.rkecom.data.product.repository.ProductRepository;
import com.rkecom.data.user.repository.AddressRepository;
import com.rkecom.db.entity.order.Order;
import com.rkecom.db.entity.order.OrderItem;
import com.rkecom.db.entity.payment.Payment;
import com.rkecom.db.entity.product.Cart;
import com.rkecom.db.entity.product.CartItem;
import com.rkecom.db.entity.product.Product;
import com.rkecom.db.entity.user.Address;
import com.rkecom.objects.order.mapper.OrderMapper;
import com.rkecom.web.order.model.OrderModel;
import com.rkecom.web.order.model.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final CartRepository cartRepository;
    private final AddressRepository addressRepository;
    private final OrderMapper orderMapper;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public OrderModel placeOrder(String email, OrderRequest orderRequest, String paymentType) {
        Cart cart = cartRepository.findCartByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException(ResourceConstants.CART, "email", email)
        );
        List<CartItem> cartItems = cart.getCartItems();
        if(cartItems.isEmpty ()){
            throw new ApiException ( "cart is empty" );
        }
        Address address = addressRepository.findById(orderRequest.getAddressId()).orElseThrow(
                () -> new ResourceNotFoundException(ResourceConstants.ADDRESS, "id", orderRequest.getAddressId())
        );

        // Create Order
        Order order = new Order();
        order.setEmail(email);
        order.setOrderDate(LocalDate.now());
        order.setTotalAmount(cart.getTotalPrice());
        order.setShippingAddress(address);
        order.setOrderStatus("order accepted");

        // Create Payment and Associate it with Order
        Payment payment = new Payment();
        payment.setPaymentType(paymentType);
        payment.setPgName(orderRequest.getPgName());
        payment.setPgStatus(orderRequest.getPgStatus());
        payment.setPgPaymentId(orderRequest.getPgPaymentId());

        order.setPayment(payment); // Associate payment with order
        payment.setOrder(order); // Bidirectional link

        // Process Order Items
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setDiscount(cartItem.getDiscount());

            order.addOrderItem(orderItem); // Ensures bidirectional mapping
        }

        // Persist Order (Cascades OrderItems and Payment)
        order = orderRepository.save(order);

        // Update Product Inventory and Remove from Cart
        List<CartItem> itemsToRemove = new ArrayList <> (cartItems); // Avoid modifying cartItems during iteration

        for (CartItem cartItem : itemsToRemove) {
            Product product = cartItem.getProduct();
            product.setQuantity(product.getQuantity() - cartItem.getQuantity());
            productRepository.save(product); // Save the updated quantity

            cartService.deleteProductFromCart(product.getProductId());
        }



        return orderMapper.toModel().apply(order);
    }


}
