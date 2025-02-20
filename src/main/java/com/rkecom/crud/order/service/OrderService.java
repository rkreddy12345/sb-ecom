package com.rkecom.crud.order.service;

import com.rkecom.crud.core.service.BaseService;
import com.rkecom.web.order.model.OrderModel;
import com.rkecom.web.order.model.OrderRequest;

public interface OrderService extends BaseService {
    OrderModel placeOrder(String email, OrderRequest orderRequest, String paymentType);
}
