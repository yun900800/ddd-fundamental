package org.ddd.fundamental.model.impl;

import org.ddd.fundamental.constants.ItemType;

public class FinishedProduct extends Item{

    public FinishedProduct(String name) {
        super(name);
    }

    @Override
    String typeName() {
        return ItemType.FINISHED_PRODUCT.getName();
    }

    @Override
    public ItemType type() {
        return ItemType.FINISHED_PRODUCT;
    }
}
