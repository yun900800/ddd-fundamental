package org.ddd.fundamental.workorder.domain.repository;

import org.ddd.fundamental.core.repository.BaseHibernateRepository;
import org.ddd.fundamental.core.repository.BaseRepository;
import org.ddd.fundamental.workorder.domain.model.ProductOrder;
import org.ddd.fundamental.workorder.value.OrderId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ProductOrderRepository extends BaseHibernateRepository<ProductOrder>,
        BaseRepository<ProductOrder, OrderId> {

    @Modifying
    @Query("delete from ProductOrder")
    void deleteAllProductOrders();
}
