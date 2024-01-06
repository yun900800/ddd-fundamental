package org.ddd.fundamental.repository.core;

import java.io.Serializable;

public abstract class EntityModel<Id extends Comparable<Id>> implements Serializable {

    private Id id;

    private boolean isUpdateDirty;

    private boolean isDeleteDirty;

    public EntityModel(Id id) {
        this.id = id;
        this.isUpdateDirty = false;
        this.isDeleteDirty = false;
    }

    public boolean isUpdateDirty() {
        return isUpdateDirty;
    }

    public void updateDirty() {
        this.isUpdateDirty = true;
    }

    public boolean isDeleteDirty() {
        return isDeleteDirty;
    }

    public void deleteDirty() {
        this.isDeleteDirty = true;
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }
}
