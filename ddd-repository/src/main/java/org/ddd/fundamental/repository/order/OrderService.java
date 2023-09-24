package org.ddd.fundamental.repository.order;

import org.ddd.fundamental.repository.core.repository.impl.RepositoryBase;
import org.ddd.fundamental.repository.core.transaction.TransactionScope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OrderService {

    @Resource
    private OrderRepository orderRepository;

    public void cancel(Long orderId) {
        Order order = this.orderRepository.findBy(orderId);
        //order.cancel();
        this.orderRepository.update(order); //(1)

        TransactionScope transactionScope = TransactionScope.create((RepositoryBase) orderRepository);//(2)
        transactionScope.commit();
    }
}
