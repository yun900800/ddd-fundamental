package org.ddd.fundamental.repository.service;

import org.ddd.fundamental.repository.core.repository.impl.RepositoryBase;
import org.ddd.fundamental.repository.core.transaction.TransactionScope;
import org.ddd.fundamental.repository.order.Order;
import org.ddd.fundamental.repository.order.OrderItem;
import org.ddd.fundamental.repository.order.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void changeOrderName(String name, Long orderId) {
        Order order = this.loadOrder(orderId);
        order.dirty();
        order.changeName(name);
        TransactionScope transactionScope = TransactionScope.create((RepositoryBase) orderRepository);//(2)
        this.orderRepository.update(order);
        transactionScope.commit();
    }

    @Transactional
    public void changeOrderItemQty(String name, int qty, Long orderId) {
        Order order = this.loadOrder(orderId);
        List<OrderItem> orderItemList = order.getOrderItems().stream().map(v->{
            if (v.getName().equals(name)) {
                v.changeQuantity(qty);
                v.dirty();
                return v;
            } else {
                return v;
            }
        }).collect(Collectors.toList());
        order.clear().addOrderItems(orderItemList);
        order.dirty();
        TransactionScope transactionScope = TransactionScope.create((RepositoryBase) orderRepository);//(2)
        this.orderRepository.update(order);
        transactionScope.commit();
    }



}
