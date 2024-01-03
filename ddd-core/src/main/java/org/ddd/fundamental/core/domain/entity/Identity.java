package org.ddd.fundamental.core.domain.entity;

public class Identity<TID extends Comparable<TID>> extends ValueModel {
    private TID id;

    public Identity(TID id){
        this.id = id;
    }


    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if(!(obj instanceof Identity)){
            return false;
        }
        if(obj == this){
            return true;
        }
        return this.id.compareTo((TID)((Identity)obj).getId()) == 0;
    }

    public TID getId() {
        return id;
    }
}
