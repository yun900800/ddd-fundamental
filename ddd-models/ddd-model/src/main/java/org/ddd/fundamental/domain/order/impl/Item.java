package org.ddd.fundamental.domain.order.impl;

import org.ddd.fundamental.constants.ItemType;
import org.ddd.fundamental.domain.order.IItem;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public abstract class Item implements IItem<String> , Serializable {

    private String name;

    private double quantity;

    private String key;

    public Item(String name,double quantity){
        this.name = name;
        this.quantity = quantity;
    }

    public void setKey(String key){
        this.key = key;
    }

    @Override
    public String key() {
        if (!StringUtils.hasLength(this.key)) {
            this.key = UUID.randomUUID().toString();
        }
        return key;
    }

    @Override
    public String name() {
        return name;
    }

    protected abstract String typeName();

    @Override
    public double quantity() {
        return this.quantity;
    }

    public Item changeQuantity(double quantity){
        this.quantity = quantity;
        return this;
    }

}
