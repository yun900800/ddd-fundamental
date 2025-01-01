package org.ddd.fundamental.workorder.domain.repository;

import org.ddd.fundamental.core.repository.BaseHibernateRepository;
import org.ddd.fundamental.core.repository.BaseRepository;
import org.ddd.fundamental.workorder.domain.model.ProductOrder;
import org.ddd.fundamental.workorder.value.OrderId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ProductOrderRepository extends BaseHibernateRepository<ProductOrder>,
        BaseRepository<ProductOrder, OrderId> ,CustomProductOrderRepository{

    @Modifying
    @Query("delete from ProductOrder")
    void deleteAllProductOrders();

    Page<ProductOrder> findAllByProductOrder_ProductName(String productName,
                                            Pageable pageable);
}
