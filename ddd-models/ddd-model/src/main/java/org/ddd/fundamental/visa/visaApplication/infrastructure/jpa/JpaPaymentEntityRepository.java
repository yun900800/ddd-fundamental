package org.ddd.fundamental.visa.visaApplication.infrastructure.jpa;

import org.ddd.fundamental.visa.visaApplication.infrastructure.jpa.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPaymentEntityRepository extends JpaRepository<PaymentEntity, Long> {
}
