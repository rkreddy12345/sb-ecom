package com.rkecom.data.order.repository;

import com.rkecom.core.data.repository.BaseRepository;
import com.rkecom.db.entity.order.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends BaseRepository<Order, Long > {
}
