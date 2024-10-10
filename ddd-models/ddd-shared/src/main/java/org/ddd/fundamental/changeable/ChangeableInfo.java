package org.ddd.fundamental.changeable;

import org.ddd.fundamental.core.ValueObject;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class ChangeableInfo implements ValueObject {
    private final String name;

    private final String desc;

    private final boolean isUse;

    public ChangeableInfo(String name,String desc,boolean isUse){
        this.name = name;
        this.desc = desc;
        this.isUse = isUse;
    }

    public ChangeableInfo(String name,String desc){
        this(name,desc,false);
    }

    public static ChangeableInfo create(String name,String desc,boolean isUse){
        return new ChangeableInfo(name,desc,isUse);
    }

    public static ChangeableInfo create(String name,String desc){
        return new ChangeableInfo(name,desc);
    }

    public ChangeableInfo changeName(String name){
        return new ChangeableInfo(name,desc,isUse);
    }

    public ChangeableInfo changeDesc(String desc){
        return new ChangeableInfo(name,desc,isUse);
    }

    public ChangeableInfo enableUse(){
        return new ChangeableInfo(name,desc,true);
    }

    public ChangeableInfo disableUse(){
        return new ChangeableInfo(name,desc);
    }

    @Override
    public @NotNull String toString() { // <4>
        return "ChangeableInfo{" +
                "name=" + name +
                ", desc=" + desc +
                ", isUse=" + isUse +
                '}';
    }

    @Override
    public boolean equals(Object o) { // <5>
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChangeableInfo that = (ChangeableInfo) o;
        return Objects.equals(name, that.name)
                && Objects.equals(desc, that.desc)
                && Objects.equals(isUse, that.isUse);
    }

    @Override
    public int hashCode() { // <6>
        return Objects.hash(name,desc,isUse);
    }

}
