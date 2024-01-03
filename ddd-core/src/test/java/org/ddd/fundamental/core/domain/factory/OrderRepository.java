package org.ddd.fundamental.core.domain.factory;

public class OrderRepository {
    private OrderMapper orderMapper;

    public Order findBy(Long id) {
        OrderDataEntity entity = this.orderMapper.getById(id);
        OrderVO orderInfo = OrderVO.of(entity);

        return OrderFactory.INSTANCE.load(orderInfo);
    }
}
