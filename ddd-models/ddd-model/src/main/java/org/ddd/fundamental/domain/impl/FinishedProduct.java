package org.ddd.fundamental.domain.impl;

import org.ddd.fundamental.constants.ItemType;

public class FinishedProduct extends Item{

    public FinishedProduct(String name, double quantity) {
        super(name, quantity);
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
