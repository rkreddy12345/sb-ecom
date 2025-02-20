package com.rkecom.web.order.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private Long addressId;
    private String paymentType;
    private String pgName;
    private String pgPaymentId;
    private String pgStatus;
}
