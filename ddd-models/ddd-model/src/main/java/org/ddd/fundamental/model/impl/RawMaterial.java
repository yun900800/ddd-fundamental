package org.ddd.fundamental.model.impl;

import org.ddd.fundamental.constants.ItemType;

public class RawMaterial extends Item {

    public RawMaterial(String name) {
        super(name);
    }

    @Override
    public ItemType type() {
        return ItemType.RAW_MATERIAL;
    }

    public String typeName() {
        return  ItemType.RAW_MATERIAL.getName();
    }

}
