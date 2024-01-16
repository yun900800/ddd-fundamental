package org.ddd.fundamental.core.domain.entity;

public abstract class ValueObject {
    public abstract boolean equals(Object obj);

    public abstract int hashCode();
}
