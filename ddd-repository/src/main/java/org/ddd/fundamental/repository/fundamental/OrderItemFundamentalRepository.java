package org.ddd.fundamental.repository.fundamental;

import org.ddd.fundamental.repository.fundamental.model.OrderItemModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemFundamentalRepository extends JpaRepository<OrderItemModel, Long> {
}
