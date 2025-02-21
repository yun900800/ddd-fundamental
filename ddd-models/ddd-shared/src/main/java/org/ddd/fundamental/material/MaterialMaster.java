package org.ddd.fundamental.material;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.changeable.NameDescInfo;
import org.ddd.fundamental.core.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Embeddable
@MappedSuperclass
@Slf4j
public class MaterialMaster implements ValueObject, Cloneable{

    /**
     * name和desc描述信息
     */
    private NameDescInfo nameDescInfo;

    /**
     * 物料特征信息定义,包括编码,规则型号，单位
     */
    private MaterialCharacter materialCharacter;

    @SuppressWarnings("unused")
    protected MaterialMaster() {
    }

    private MaterialMaster(NameDescInfo nameDescInfo,MaterialCharacter materialCharacter){
        this.nameDescInfo = nameDescInfo;
        this.materialCharacter = materialCharacter;
    }

    private MaterialMaster(@NotNull String code, @NotNull String name
            ,@NotNull String spec,@NotNull String unit){
        Objects.requireNonNull(code,"code must not be null");
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(spec, "spec must not be null");
        Objects.requireNonNull(unit, "unit must not be null");
        this.nameDescInfo = NameDescInfo.create(name,name);
        this.materialCharacter = MaterialCharacter.create(code,spec,unit);
    }

    public NameDescInfo getNameDescInfo() {
        return nameDescInfo;
    }

    public MaterialCharacter getMaterialCharacter() {
        return materialCharacter;
    }

    public static MaterialMaster create(NameDescInfo nameDescInfo, MaterialCharacter materialCharacter){
        return new MaterialMaster(nameDescInfo,materialCharacter);
    }

    public static MaterialMaster create(String code,String name,String spec,String unit){
        return new MaterialMaster(code, name, spec,unit);
    }

    public @NotNull String getCode() {
        return materialCharacter.getCode();
    }

    public @NotNull String getName() {
        return nameDescInfo.getName();
    }

    public @NotNull String getSpec() {
        return materialCharacter.getSpec();
    }

    public @NotNull String getUnit() {
        return materialCharacter.getUnit();
    }

    @Override
    public boolean equals(Object o) { // <7>
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MaterialMaster that = (MaterialMaster) o;
        return nameDescInfo.equals(that.nameDescInfo)
                && materialCharacter.equals(that.materialCharacter);
    }

    @Override
    public int hashCode() { // <8>
        return Objects.hash(nameDescInfo, materialCharacter);
    }

    @Override
    public String toString() {
        return objToString();
    }

    @Override
    public MaterialMaster clone() {
        try {
            MaterialMaster clone = (MaterialMaster) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
