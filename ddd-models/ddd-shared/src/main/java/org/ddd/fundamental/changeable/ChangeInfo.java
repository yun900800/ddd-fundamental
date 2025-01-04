package org.ddd.fundamental.changeable;

import org.ddd.fundamental.core.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass
@Embeddable
public class ChangeInfo implements ValueObject,Cloneable {

    private NameDescInfo nameDescInfo;

    private boolean isUse;

    @SuppressWarnings("unused")
    protected ChangeInfo(){}

    private ChangeInfo(NameDescInfo nameDescInfo,
                       boolean isUse){
        this.nameDescInfo = nameDescInfo;
        this.isUse = isUse;
    }

    public static ChangeInfo create(NameDescInfo nameDescInfo,
                                    boolean isUse){
        return new ChangeInfo(nameDescInfo, isUse);
    }

    public ChangeInfo changeInfo(NameDescInfo info){
        this.nameDescInfo = info;
        return this;
    }

    public ChangeInfo enableUse(){
        this.isUse = true;
        return this;
    }

    public ChangeInfo disableUse(){
        this.isUse = false;
        return this;
    }

    public NameDescInfo getNameDescInfo() {
        return nameDescInfo.clone();
    }

    public boolean isUse() {
        return isUse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChangeInfo)) return false;
        ChangeInfo that = (ChangeInfo) o;
        return isUse == that.isUse && Objects.equals(nameDescInfo, that.nameDescInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameDescInfo, isUse);
    }

    @Override
    public String toString() {
        return objToString();
    }

    @Override
    public ChangeInfo clone() {
        try {
            ChangeInfo clone = (ChangeInfo) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
