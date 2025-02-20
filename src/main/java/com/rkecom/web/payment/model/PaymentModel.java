package com.rkecom.web.payment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentModel {
    private Long paymentId;
    private String paymentType;
    private String pgPaymentId;
    private String pgStatus;
    private String pgName;
}
