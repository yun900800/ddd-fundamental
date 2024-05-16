package org.ddd.fundamental.domain.inventory.impl;

import org.ddd.fundamental.constants.ItemType;

public class WorkInInventoryItem extends InventoryItem{
    public WorkInInventoryItem(String materialNumber, String storeHouseKey, String name, double quantity) {
        super(materialNumber, storeHouseKey, name, quantity);
    }

    @Override
    public ItemType type() {
        return ItemType.WORK_IN_PROGRESS;
    }

    @Override
    protected String typeName() {
        return ItemType.WORK_IN_PROGRESS.getName();
    }
}
