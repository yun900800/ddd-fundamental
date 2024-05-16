package org.ddd.fundamental.domain.order.impl;

import org.ddd.fundamental.constants.ItemType;
import org.ddd.fundamental.domain.order.IItem;
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

    public void setKey(String key){
        this.key = key;
    }

    public static Item copy(IItem<String> item){
        Item copyItem = new Item(item.name(),item.quantity()) {
            @Override
            protected String typeName() {
                return item.type().getName();
            }

            @Override
            public ItemType type() {
                return item.type();
            }
        };
        copyItem.setKey(item.key());
        return copyItem;
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
