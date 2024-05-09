package org.ddd.fundamental.domain.impl;

import org.ddd.fundamental.constants.ItemType;

public class RawMaterial extends Item {

    public RawMaterial(String name, double quantity) {
        super(name,quantity);
    }

    @Override
    public ItemType type() {
        return ItemType.RAW_MATERIAL;
    }

    public String typeName() {
        return  ItemType.RAW_MATERIAL.getName();
    }

}
