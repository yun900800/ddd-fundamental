package org.ddd.fundamental.material;

import org.ddd.fundamental.core.ValueObject;
import org.ddd.fundamental.exception.NotSplitException;
import org.springframework.lang.Nullable;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Embeddable
@MappedSuperclass
public class ComputableObject implements ValueObject {

    private String unit;

    private double qty;

    private String qrCode;

    @SuppressWarnings("unused")
    ComputableObject() {
    }

    public ComputableObject(@NotNull String unit, @NotNull double qty, @Nullable String qrCode){
        this.unit = Objects.requireNonNull(unit,"unit must not be null");
        this.qty = Objects.requireNonNull(qty,"qty must not be null");
        this.qrCode = qrCode;
    }

    public static <T extends ComputableObject> List<T> split(T computableObject){
        notSplitCheck(computableObject);
        SplitStrategy<T> strategy = new DividedTwoStrategyByClass(computableObject.getClass(),
                new Class[]{computableObject.getClass()});
        return strategy.split(computableObject);
    }

    public static <T extends ComputableObject> List<T> split1(T computableObject){
        notSplitCheck(computableObject);
        SplitStrategy<T> strategy = new DividedTwoStrategy();
        return strategy.split(computableObject);
    }

    private static <T extends ComputableObject> void notSplitCheck(T computableObject){
        if (computableObject.getQty() == 1) {
            throw new NotSplitException("qty is one,can't split");
        }
    }


    public <T extends ComputableObject> List<T> split2(T computableObject,
                                                       SplitStrategy<T> strategy){
        notSplitCheck(computableObject);
        return strategy.split(computableObject);
    }

    public ComputableObject changeQty(double qty){
        this.qty = qty;
        return this;
    }



    public @NotNull String getUnit() {
        return unit;
    }

    public @NotNull double getQty() {
        return qty;
    }

    public String getQrCode() {
        return qrCode;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComputableObject that = (ComputableObject) o;
        return unit.equals(that.unit)
                && qty == that.qty
                && qrCode.equals(that.qrCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(unit, qty, qrCode);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(unit);
        sb.append(", ");
        sb.append(qty);
        sb.append(", ");
        sb.append(qrCode);
        return sb.toString();
    }
}
