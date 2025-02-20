package com.rkecom.objects.order.mapper;

import com.rkecom.db.entity.order.Order;
import com.rkecom.db.entity.order.OrderItem;
import com.rkecom.objects.product.mapper.ProductMapper;
import com.rkecom.web.order.model.OrderItemModel;
import com.rkecom.web.order.model.OrderModel;
import com.rkecom.web.payment.model.PaymentModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final ProductMapper productMapper;

    public Function < Order, OrderModel > toModel() {
        return order -> OrderModel.builder()
                .orderId(order.getOrderId())
                .email(order.getEmail())
                .orderItems(toOrderItemModelsList().apply(order.getOrderItems()))
                .orderDate(order.getOrderDate())
                .payment(toPaymentModel().apply(order))
                .totalAmount(order.getTotalAmount())
                .orderStatus(order.getOrderStatus())
                .addressId(order.getShippingAddress ().getAddressId ())
                .build();
    }

    private Function< List < OrderItem >, List< OrderItemModel >> toOrderItemModelsList() {
        return orderItems -> orderItems.stream()
                .map(orderItem -> OrderItemModel.builder()
                        .orderItemId(orderItem.getOrderItemId())
                        .product(productMapper.toModel().apply(orderItem.getProduct()))
                        .quantity(orderItem.getQuantity())
                        .discount(orderItem.getDiscount())
                        .price(orderItem.getPrice())
                        .build())
                .toList ();
    }

    private Function<Order, PaymentModel > toPaymentModel() {
        return order -> PaymentModel.builder()
                .paymentId(order.getPayment().getPaymentId())
                .paymentType (order.getPayment().getPaymentType ())
                .pgPaymentId (order.getPayment().getPgPaymentId ())
                .pgName ( order.getPayment().getPgName () )
                .pgStatus ( order.getPayment().getPgStatus () )
                .build();
    }

}
