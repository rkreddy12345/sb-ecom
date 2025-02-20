package com.rkecom.data.order.repository;

import com.rkecom.core.data.repository.BaseRepository;
import com.rkecom.db.entity.order.OrderItem;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends BaseRepository< OrderItem, Long> {
}
