package org.ddd.fundamental.changeable;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.core.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Embeddable
@MappedSuperclass
public class ChangeableInfo implements ValueObject,Cloneable {
    private String name;

    private String desc;

    private boolean isUse;

    @SuppressWarnings("unused")
    protected ChangeableInfo(){}
    private ChangeableInfo(String name,String desc,boolean isUse){
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

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }


    public boolean isUse() {
        return isUse;
    }


    /**
     * jackson 反序列化依赖于这个方法,
     * 可以配置ObjectMapper反序列化不依赖于这个方法
     * @param use
     */
    public void setUse(boolean use) {
        this.isUse = use;
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

    @Override
    public @NotNull String toString() {
        return objToString();
    }

    @Override
    public ChangeableInfo clone() {
        try {
            ChangeableInfo clone = (ChangeableInfo) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
