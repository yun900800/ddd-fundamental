package org.ddd.fundamental.invoice.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, InvoiceId> {
    //@NonNull
    //Stream<Invoice> findByOrderId(@NonNull OrderId orderId);
}
