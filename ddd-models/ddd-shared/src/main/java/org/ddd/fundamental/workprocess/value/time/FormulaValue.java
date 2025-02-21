package org.ddd.fundamental.workprocess.value.time;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.changeable.NameDescInfo;
import org.ddd.fundamental.core.ValueObject;
import org.ddd.fundamental.material.value.MaterialInfo;

import javax.persistence.*;
import java.util.Objects;

@MappedSuperclass
@Embeddable
@Slf4j
public class FormulaValue implements ValueObject, Cloneable {

    @AttributeOverrides({
                    @AttributeOverride(name = "name", column = @Column(name = "formula_name")),
                    @AttributeOverride(name = "desc", column = @Column(name = "formula_desc"))
            }
    )
    private NameDescInfo info;

    /**
     * 配方的原材料信息
     */
    private MaterialInfo rawMaterialInfo;

    /**
     * 辅料信息
     */
    private MaterialInfo spareMaterialInfo;

    /**
     * 添加剂信息
     */
    private MaterialInfo addedMaterialInfo;

    @SuppressWarnings("unused")
    protected FormulaValue(){}

    private FormulaValue(NameDescInfo info,MaterialInfo rawMaterialInfo,
                         MaterialInfo spareMaterialInfo,
                         MaterialInfo addedMaterialInfo){
        this.info = info;
        this.addedMaterialInfo = addedMaterialInfo;
        this.rawMaterialInfo = rawMaterialInfo;
        this.spareMaterialInfo = spareMaterialInfo;
    }

    public static FormulaValue create(NameDescInfo info,MaterialInfo rawMaterialInfo,
                                      MaterialInfo spareMaterialInfo,
                                      MaterialInfo addedMaterialInfo){
        return new FormulaValue(info, rawMaterialInfo,spareMaterialInfo,addedMaterialInfo);
    }

    public NameDescInfo getInfo() {
        return info.clone();
    }

    public MaterialInfo getRawMaterialInfo() {
        return rawMaterialInfo.clone();
    }

    public MaterialInfo getSpareMaterialInfo() {
        return spareMaterialInfo.clone();
    }

    public MaterialInfo getAddedMaterialInfo() {
        return addedMaterialInfo.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FormulaValue)) return false;
        FormulaValue that = (FormulaValue) o;
        return Objects.equals(info, that.info) && Objects.equals(rawMaterialInfo, that.rawMaterialInfo)
                && Objects.equals(spareMaterialInfo, that.spareMaterialInfo) && Objects.equals(addedMaterialInfo, that.addedMaterialInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(info, rawMaterialInfo, spareMaterialInfo, addedMaterialInfo);
    }

    @Override
    public String toString() {
        return objToString();
    }

    @Override
    public FormulaValue clone() {
        try {
            FormulaValue clone = (FormulaValue) super.clone();
            clone.info = info.clone();
            clone.rawMaterialInfo = rawMaterialInfo.clone();
            clone.spareMaterialInfo = spareMaterialInfo.clone();
            clone.addedMaterialInfo = addedMaterialInfo.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
