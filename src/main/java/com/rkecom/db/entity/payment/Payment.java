package com.rkecom.db.entity.payment;

import com.rkecom.db.entity.order.Order;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "payment")
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @OneToOne(mappedBy = "payment", cascade = CascadeType.PERSIST)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Order order;
    @Column(name = "payment_type")
    private String paymentType;

    //pg->payment gateway
    private String pgPaymentId;
    private String pgStatus;
    private String pgName;
}
