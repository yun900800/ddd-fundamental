package org.ddd.fundamental.domain.impl;

import org.ddd.fundamental.constants.ItemType;

public class WorkInProgress extends Item {


    public WorkInProgress(String name, double quantity) {
        super(name,quantity);
    }

    @Override
    String typeName() {
        return ItemType.WORK_IN_PROGRESS.getName();
    }

    @Override
    public ItemType type() {
        return ItemType.WORK_IN_PROGRESS;
    }
}
