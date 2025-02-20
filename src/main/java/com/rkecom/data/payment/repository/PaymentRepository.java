package com.rkecom.data.payment.repository;

import com.rkecom.core.data.repository.BaseRepository;
import com.rkecom.db.entity.payment.Payment;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends BaseRepository< Payment, Long > {
}
