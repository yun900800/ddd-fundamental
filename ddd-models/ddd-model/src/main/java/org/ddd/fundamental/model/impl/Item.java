package org.ddd.fundamental.model.impl;

import org.ddd.fundamental.constants.ItemType;
import org.ddd.fundamental.model.IItem;
import org.springframework.util.StringUtils;

import java.util.UUID;

public abstract class Item implements IItem<String> {

    private String name;

    private String key;

    public Item(String name){
        this.name = name;
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

}
