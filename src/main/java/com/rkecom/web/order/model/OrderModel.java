package com.rkecom.web.order.model;

import com.rkecom.web.payment.model.PaymentModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderModel {
    private Long orderId;
    private String email;
    private List <OrderItemModel> orderItems;
    private LocalDate orderDate;
    private PaymentModel payment;
    private BigDecimal totalAmount;
    private String orderStatus;
    private Long addressId;
}
