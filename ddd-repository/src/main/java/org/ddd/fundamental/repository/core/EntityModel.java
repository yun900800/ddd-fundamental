package org.ddd.fundamental.repository.core;

public abstract class EntityModel<Id extends Comparable<Id>> {

    private Id id;

    public EntityModel(Id id) {
        this.id = id;
    }

    public Id getId() {
        return id;
    }
}
