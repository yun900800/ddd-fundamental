package org.ddd.fundamental.repository.service;

import org.ddd.fundamental.repository.core.repository.impl.RepositoryBase;
import org.ddd.fundamental.repository.core.transaction.TransactionScope;
import org.ddd.fundamental.repository.order.Order;
import org.ddd.fundamental.repository.order.OrderItem;
import org.ddd.fundamental.repository.order.OrderRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

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

    public void save(Order order) {
        TransactionScope transactionScope = TransactionScope.create((RepositoryBase) orderRepository);//(2)
        this.orderRepository.add(order);
        transactionScope.commit();
    }

    public Order loadOrder(Long id) {
       return this.orderRepository.findBy(id);
    }

    public void changeOrderName(String name, Long orderId) {
        Order order = this.loadOrder(orderId);
        order.changeName(name);
        TransactionScope transactionScope = TransactionScope.create((RepositoryBase) orderRepository);//(2)
        this.orderRepository.update(order);
        transactionScope.commit();
    }

    public void changeOrderItemQty(String name, int qty, Long orderId) {
        Order order = this.loadOrder(orderId);
        List<OrderItem> orderItemList = order.getOrderItems().stream().filter(v->v.getName().equals(name)).collect(Collectors.toList());
        orderItemList.stream().map(v->{
            v.changeQuantity(qty);
            return this;
        }).collect(Collectors.toList());
        List<OrderItem> notChangedOrderItemList = order.getOrderItems().stream().filter(v->!v.getName().equals(name)).collect(Collectors.toList());
        order.clear().addOrderItems(orderItemList).addOrderItems(notChangedOrderItemList);
        TransactionScope transactionScope = TransactionScope.create((RepositoryBase) orderRepository);//(2)
        this.orderRepository.update(order);
        transactionScope.commit();
    }



}
