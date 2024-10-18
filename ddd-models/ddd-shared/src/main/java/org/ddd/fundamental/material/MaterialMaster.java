package org.ddd.fundamental.material;

import org.ddd.fundamental.core.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Embeddable
@MappedSuperclass
public class MaterialMaster implements ValueObject {

    /**
     * 物料编码
     */
    private String code;

    /**
     * 物料名称
     */
    private String name;

    /**
     * 规格型号
     */
    private String spec;

    /**
     * 单位
     */
    private String unit;

    @SuppressWarnings("unused")
    MaterialMaster() { // <3>
    }

    public MaterialMaster(@NotNull String code, @NotNull String name
            ,@NotNull String spec,@NotNull String unit){
        this.code = Objects.requireNonNull(code,"code must not be null");
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.spec = Objects.requireNonNull(spec, "spec must not be null");
        this.unit = Objects.requireNonNull(unit, "unit must not be null");
    }

    public @NotNull String getCode() {
        return code;
    }

    public @NotNull String getName() {
        return name;
    }

    public @NotNull String getSpec() {
        return spec;
    }

    public @NotNull String getUnit() {
        return unit;
    }

    @Override
    public boolean equals(Object o) { // <7>
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MaterialMaster that = (MaterialMaster) o;
        return code.equals(that.code)
                && name.equals(that.name)
                && spec.equals(that.spec)
                && unit.equals(that.unit);
    }

    @Override
    public int hashCode() { // <8>
        return Objects.hash(code, name, spec,unit);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(code);
        sb.append(", ");
        sb.append(name).append(" ").append(spec);
        sb.append(", ");
        sb.append(unit);
        return sb.toString();
    }
}
