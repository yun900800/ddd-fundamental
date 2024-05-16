package org.ddd.fundamental.domain.inventory.impl;

import org.ddd.fundamental.constants.ItemType;
import org.ddd.fundamental.domain.inventory.IStoreHouse;

import java.util.UUID;

/**
 * 一个基本的仓库
 */
public class StoreHouse implements IStoreHouse<String> {

    private String key;

    private String name;

    private ItemType storeType;

    public StoreHouse(String name, ItemType storeType){
        this.key = UUID.randomUUID().toString();
        this.name = name;
        this.storeType = storeType;
    }

    @Override
    public String key() {
        return key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public ItemType storeType() {
        return storeType;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String toString() {
        return "StoreHouse{" +
                "key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", storeType='" + storeType.getName() + '\'' +
                '}';
    }
}
