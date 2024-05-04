package org.ddd.fundamental.model.impl;

import org.ddd.fundamental.constants.ItemType;

public class WorkInProgress extends Item {


    public WorkInProgress(String name) {
        super(name);
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
