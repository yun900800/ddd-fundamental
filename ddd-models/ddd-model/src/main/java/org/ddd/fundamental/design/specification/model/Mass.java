package org.ddd.fundamental.design.specification.model;

import lombok.Getter;

@Getter
public class Mass {
    private final Double value;
    public Mass(Double value){
        this.value = value;
    }

    public boolean greaterThan(Mass mass){
        return this.value.compareTo(mass.getValue())>0 ? true: false;
    }

    public boolean equal(Mass mass){
        return this.value.compareTo(mass.getValue()) == 0;
    }

}
