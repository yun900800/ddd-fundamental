package org.ddd.fundamental.equipment.value;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.core.ValueObject;
import org.ddd.fundamental.equipment.ProductInput;
import org.ddd.fundamental.equipment.ProductOutput;
import org.ddd.fundamental.equipment.ProductResources;
import org.ddd.fundamental.equipment.enums.ToolingType;

import javax.persistence.*;
import java.util.Objects;

@Embeddable
@MappedSuperclass
public class ToolingEquipmentValueObject implements ValueObject, ProductResources, Cloneable {

    @AttributeOverrides({
            @AttributeOverride(name  = "name", column = @Column(name = "equipment_name", nullable = false)),
            @AttributeOverride(name  = "desc", column = @Column(name = "equipment_desc", nullable = false))
    })
    private ChangeableInfo toolingEquipment;

    /**
     * 工装类型
     */
    @Enumerated(EnumType.STRING)
    private ToolingType toolingType;

    /**
     * 规格型号
     */
    private String specification;

    private MaintainStandard standard;

    @SuppressWarnings("unused")
    private ToolingEquipmentValueObject(){}

    private ToolingEquipmentValueObject(ChangeableInfo toolingEquipment,
                                       MaintainStandard standard,
                                        ToolingType toolingType,
                                        String specification ){
        this.toolingEquipment = toolingEquipment;
        this.standard = standard;
        this.toolingType = toolingType;
        this.specification = specification;
    }

    public static ToolingEquipmentValueObject create(ChangeableInfo toolingEquipment,
                                                     MaintainStandard standard){
        return new ToolingEquipmentValueObject(toolingEquipment,standard,
                ToolingType.GZ,"默认没有规格型号");
    }

    public static ToolingEquipmentValueObject create(ChangeableInfo toolingEquipment,
                                                     MaintainStandard standard,
                                                     ToolingType toolingType, String specification){
        return new ToolingEquipmentValueObject(toolingEquipment,standard,
                toolingType,specification);
    }

    public ToolingEquipmentValueObject enableUse(){
        this.toolingEquipment = this.toolingEquipment.enableUse();
        return this;
    }

    @Override
    public void input(ProductInput input) {

    }

    @Override
    public ProductOutput output() {
        return null;
    }

    @Override
    public String resourceName() {
        return toolingEquipment.getName();
    }

    public ChangeableInfo getToolingEquipment() {
        return toolingEquipment.clone();
    }

    public MaintainStandard getStandard() {
        return standard.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ToolingEquipmentValueObject)) return false;
        ToolingEquipmentValueObject that = (ToolingEquipmentValueObject) o;
        return Objects.equals(toolingEquipment, that.toolingEquipment) && Objects.equals(standard, that.standard)
                && Objects.equals(toolingType, that.toolingType) && Objects.equals(specification, that.specification);
    }

    @Override
    public int hashCode() {
        return Objects.hash(toolingEquipment, standard, toolingType, specification);
    }

    @Override
    public String toString() {
        return "ToolingEquipmentValueObject{" +
                "toolingEquipment=" + toolingEquipment +
                ", standard=" + standard +
                '}';
    }

    @Override
    public ToolingEquipmentValueObject clone() {
        try {
            ToolingEquipmentValueObject clone = (ToolingEquipmentValueObject) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
