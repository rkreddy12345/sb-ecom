package com.rkecom.db.entity.order;

import com.rkecom.db.entity.payment.Payment;
import com.rkecom.db.entity.user.Address;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ecom_order")
@Data
public class Order {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Setter ( AccessLevel.NONE)
    @Column(name = "order_id")
    private Long orderId;
    @Column (name = "email", nullable = false, length = 50)
    private String email;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List <OrderItem> orderItems=new ArrayList <> ();
    private LocalDate orderDate;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "payment_id")
    private Payment payment;
    private BigDecimal totalAmount;
    private String orderStatus;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address shippingAddress;

    public void addOrderItem( OrderItem item) {
        orderItems.add(item);
        item.setOrder (this);// Synchronize the bidirectional relationship
    }

}
