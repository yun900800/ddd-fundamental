package org.ddd.fundamental.material;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.core.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

/**
 * 物料的特征包含单个属性
 */

@Embeddable
@MappedSuperclass
@Slf4j
public class MaterialCharacter implements ValueObject, Cloneable {

    /**
     * 物料编码
     */
    private String code;

    /**
     * 规则型号
     */
    private String spec;

    /**
     * 物料单位
     */
    private String unit;

    @SuppressWarnings("unused")
    protected MaterialCharacter() {
    }

    private MaterialCharacter(String code,String spec,
                              String unit){
        this.code = code;
        this.spec = spec;
        this.unit = unit;
    }

    public static MaterialCharacter create(String code,String spec,
                                           String unit){
        return new MaterialCharacter(code,spec,unit);
    }

    public String getCode() {
        return code;
    }

    public String getSpec() {
        return spec;
    }

    public String getUnit() {
        return unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MaterialCharacter)) return false;
        MaterialCharacter that = (MaterialCharacter) o;
        return Objects.equals(code, that.code) && Objects.equals(spec, that.spec) && Objects.equals(unit, that.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, spec, unit);
    }

    @Override
    public String toString() {
        return objToString();
    }
}
