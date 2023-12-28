package org.ddd.fundamental.repository.fundamental;

import org.ddd.fundamental.repository.fundamental.model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderFundamentalRepository extends JpaRepository<OrderModel,Long> {
}
