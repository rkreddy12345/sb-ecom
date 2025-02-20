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
        Cart cart = getCartByEmail(email);
        if (cart.getCartItems().isEmpty()) {
            throw new ApiException("Cart is empty");
        }

        Address shippingAddress = getAddressById(orderRequest.getAddressId());
        Order order = createOrder(email, cart, shippingAddress);
        Payment payment = createPayment(orderRequest, paymentType);
        order.setPayment(payment);
        payment.setOrder(order);

        processOrderItems(cart, order);
        updateProductInventoryAndClearCart(cart);

        order = orderRepository.save(order);
        return orderMapper.toModel().apply(order);
    }

    private Cart getCartByEmail(String email) {
        return cartRepository.findCartByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceConstants.CART, "email", email));
    }

    private Address getAddressById(Long addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceConstants.ADDRESS, "id", addressId));
    }

    private Order createOrder(String email, Cart cart, Address shippingAddress) {
        Order order = new Order();
        order.setEmail(email);
        order.setOrderDate(LocalDate.now());
        order.setTotalAmount(cart.getTotalPrice());
        order.setShippingAddress(shippingAddress);
        order.setOrderStatus("Order Accepted");
        return order;
    }

    private Payment createPayment(OrderRequest orderRequest, String paymentType) {
        Payment payment = new Payment();
        payment.setPaymentType(paymentType);
        payment.setPgName(orderRequest.getPgName());
        payment.setPgStatus(orderRequest.getPgStatus());
        payment.setPgPaymentId(orderRequest.getPgPaymentId());
        return payment;
    }

    private void processOrderItems(Cart cart, Order order) {
        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setDiscount(cartItem.getDiscount());
            order.addOrderItem(orderItem);
        }
    }

    private void updateProductInventoryAndClearCart(Cart cart) {
        List<CartItem> cartItems = new ArrayList<>(cart.getCartItems()); // Prevent modification during iteration
        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();
            product.setQuantity(product.getQuantity() - cartItem.getQuantity());
            productRepository.save(product);
            cartService.deleteProductFromCart(product.getProductId());
        }
    }
}
