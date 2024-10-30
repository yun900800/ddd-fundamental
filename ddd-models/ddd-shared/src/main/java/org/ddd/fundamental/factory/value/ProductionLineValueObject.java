package org.ddd.fundamental.factory.value;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.core.ValueObject;

import javax.persistence.*;
import java.util.Objects;

@Embeddable
@MappedSuperclass
public class ProductionLineValueObject implements ValueObject,Cloneable {

    @AttributeOverrides({
            @AttributeOverride(name = "name" , column =@Column( name = "product_line_name", nullable = false)),
            @AttributeOverride(name = "desc" , column =@Column( name = "product_line_desc", nullable = false)),
            @AttributeOverride(name = "isUse" , column =@Column( name = "product_line_is_use", nullable = false))
    })
    private ChangeableInfo productLine;
    @SuppressWarnings("unused")
    private ProductionLineValueObject(){}

    public ProductionLineValueObject(ChangeableInfo info){
        this.productLine = info;
    }

    public ProductionLineValueObject changeName(String name) {
        this.productLine = this.productLine.changeName(name);
        return this;
    }

    public ProductionLineValueObject changeDesc(String desc) {
        this.productLine = this.productLine.changeDesc(desc);
        return this;
    }

    public String name(){
        return this.productLine.getName();
    }

    public String desc(){
        return this.productLine.getDesc();
    }

    public ChangeableInfo getProductLine() {
        return productLine.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductionLineValueObject)) return false;
        ProductionLineValueObject that = (ProductionLineValueObject) o;
        return Objects.equals(productLine, that.productLine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productLine);
    }

    @Override
    public String toString() {
        return "ProductionLineValueObject{" +
                "productLine=" + productLine +
                '}';
    }

    @Override
    public ProductionLineValueObject clone() {
        try {
            ProductionLineValueObject clone = (ProductionLineValueObject) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
