package org.ddd.fundamental.changeable;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.core.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@Embeddable
@MappedSuperclass
@Slf4j
public class NameDescInfo implements ValueObject,Cloneable {

    private String name;

    private String desc;

    @SuppressWarnings("unused")
    protected NameDescInfo(){
    }

    private NameDescInfo(String name,String desc){
        this.name = name;
        this.desc = desc;
    }

    public static NameDescInfo create(String name,String desc){
        return new NameDescInfo(name,desc);
    }

//    public NameDescInfo changeName(String name){
//        this.name = name;
//        return this;
//    }
//
//    public NameDescInfo changeDesc(String desc){
//        this.desc = desc;
//        return this;
//    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NameDescInfo)) return false;
        NameDescInfo that = (NameDescInfo) o;
        return Objects.equals(name, that.name) && Objects.equals(desc, that.desc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, desc);
    }

    @Override
    public String toString() {
        return objToString();
    }

    @Override
    public NameDescInfo clone() {
        try {
            NameDescInfo clone = (NameDescInfo) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
