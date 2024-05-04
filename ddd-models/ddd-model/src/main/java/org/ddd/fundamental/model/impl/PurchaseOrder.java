package org.ddd.fundamental.model.impl;

import org.ddd.fundamental.model.IItem;
import org.ddd.fundamental.model.IOrder;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PurchaseOrder implements IOrder<String> {

    private String name;

    private List<IItem<String>> items = new ArrayList<>();

    public PurchaseOrder(String name){
        this.name = name;
    }


    @Override
    public String name() {
        return name;
    }

    @Override
    public IOrder<String> nextStatus() {
        return null;
    }

    @Override
    public List<IItem<String>> items() {
        return items;
    }

    @Override
    public boolean validOrder() {
        return !CollectionUtils.isEmpty(items());
    }

    @Override
    public IOrder<String> addItem(IItem<String> item) {
        items.add(item);
        return this;
    }

    @Override
    public List<String> itemKeys() {
        List<String> keys = new ArrayList<>();
        for (IItem<String> item: items){
            keys.add(item.key());
        }
        return keys;
    }

    @Override
    public boolean contains(String key) {
        Set<String> keys = new HashSet<>(itemKeys());
        return keys.contains(key);
    }
}
