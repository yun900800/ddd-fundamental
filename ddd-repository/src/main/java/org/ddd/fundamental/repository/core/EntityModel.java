package org.ddd.fundamental.repository.core;

public abstract class EntityModel<Id extends Comparable<Id>> {

    private Id id;

    private boolean isDirty = false;

    public EntityModel(Id id) {
        this.id = id;
        this.isDirty = false;
    }

    public boolean isDirty() {
        return isDirty;
    }

    public void dirty() {
        this.isDirty = true;
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }
}
