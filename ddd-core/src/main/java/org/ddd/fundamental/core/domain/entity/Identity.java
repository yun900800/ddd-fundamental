package org.ddd.fundamental.core.domain.entity;

import java.util.Objects;

public class Identity<TID extends Comparable<TID>> extends ValueObject {
    private TID id;

    public Identity(TID id){
        this.id = id;
    }


    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof Identity)){
            return false;
        }
        if(obj == this){
            return true;
        }
        return this.id.compareTo((TID)((Identity)obj).getId()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }

    public TID getId() {
        return id;
    }
}
