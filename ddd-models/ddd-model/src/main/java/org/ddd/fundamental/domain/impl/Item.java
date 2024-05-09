package org.ddd.fundamental.domain.impl;

import org.ddd.fundamental.domain.IItem;
import org.springframework.util.StringUtils;

import java.util.UUID;

public abstract class Item implements IItem<String> {

    private String name;

    private double quantity;

    private String key;

    public Item(String name,double quantity){
        this.name = name;
        this.quantity = quantity;
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

    abstract String typeName();

    @Override
    public double quantity() {
        return this.quantity;
    }

}
