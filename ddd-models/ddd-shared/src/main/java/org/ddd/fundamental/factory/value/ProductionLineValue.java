package org.ddd.fundamental.factory.value;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.core.ValueObject;

import javax.persistence.*;
import java.util.Objects;

@Embeddable
@MappedSuperclass
public class ProductionLineValue implements ValueObject,Cloneable {

    @AttributeOverrides({
            @AttributeOverride(name = "name" , column =@Column( name = "product_line_name", nullable = false)),
            @AttributeOverride(name = "desc" , column =@Column( name = "product_line_desc", nullable = false)),
            @AttributeOverride(name = "isUse" , column =@Column( name = "product_line_is_use", nullable = false))
    })
    private ChangeableInfo productLine;
    @SuppressWarnings("unused")
    private ProductionLineValue(){}

    public ProductionLineValue(ChangeableInfo info){
        this.productLine = info;
    }

    public ProductionLineValue changeName(String name) {
        this.productLine = this.productLine.changeName(name);
        return this;
    }

    public ProductionLineValue changeDesc(String desc) {
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
        if (!(o instanceof ProductionLineValue)) return false;
        ProductionLineValue that = (ProductionLineValue) o;
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
    public ProductionLineValue clone() {
        try {
            ProductionLineValue clone = (ProductionLineValue) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
