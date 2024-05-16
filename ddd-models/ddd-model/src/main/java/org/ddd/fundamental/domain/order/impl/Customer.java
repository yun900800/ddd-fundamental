package org.ddd.fundamental.domain.order.impl;

import org.ddd.fundamental.domain.order.ICustomer;
import org.ddd.fundamental.domain.order.IOrder;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Customer implements ICustomer<String> {

    private String name;

    private String key;

    private Set<IOrder<String>> orders = new HashSet<>();

    public Customer(String key,String name){
        this.key = key;
        this.name = name;
    }

    public Customer(String name){
        this(UUID.randomUUID().toString(), name);
    }

    public Set<IOrder<String>> friendOrders() {
        return orders;
    }

    @Override
    public String key() {
        if (!StringUtils.hasLength(this.key)) {
            this.key = UUID.randomUUID().toString();
        }
        return key;
    }

    @Override
    public void setKey(String key) {

    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public void addOrder(IOrder<String> order) {
        order.setCustomer(this);
    }
}
