package org.ddd.fundamental.domain.inventory.impl;

import org.ddd.fundamental.constants.ItemType;

public class ProductInventoryItem extends InventoryItem{
    public ProductInventoryItem(String materialNumber, String storeHouseKey, String name, double quantity) {
        super(materialNumber, storeHouseKey, name, quantity);
    }

    @Override
    public ItemType type() {
        return ItemType.FINISHED_PRODUCT;
    }

    @Override
    protected String typeName() {
        return ItemType.FINISHED_PRODUCT.getName();
    }
}
