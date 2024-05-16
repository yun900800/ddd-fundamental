package org.ddd.fundamental.domain.inventory.impl;

import org.ddd.fundamental.constants.ItemType;

public class MaterialInventoryItem extends InventoryItem{

    public MaterialInventoryItem(String materialNumber, String storeHouseKey,
                                 String name, double quantity) {
        super(materialNumber, storeHouseKey, name, quantity);
    }

    @Override
    public ItemType type() {
        return ItemType.RAW_MATERIAL;
    }

    @Override
    protected String typeName() {
        return ItemType.RAW_MATERIAL.getName();
    }
}
