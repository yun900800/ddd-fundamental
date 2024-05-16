package org.ddd.fundamental.domain.inventory.impl;

import org.ddd.fundamental.constants.ItemType;
import org.ddd.fundamental.domain.inventory.IInventoryItem;
import org.ddd.fundamental.domain.order.impl.Item;


public abstract class InventoryItem extends Item implements IInventoryItem<String> {

    private String key;

    private String materialNumber;

    private String storeHouseKey;

    public InventoryItem(String materialNumber,String storeHouseKey,
                         String name, double quantity){
        super(name,quantity);
        this.materialNumber = materialNumber;
        this.storeHouseKey = storeHouseKey;
    }


    @Override
    public String materialNumber() {
        return materialNumber;
    }

    @Override
    public String storeHouseKey() {
        return storeHouseKey;
    }
}
