package org.ddd.fundamental.workprocess.value;

import org.ddd.fundamental.core.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass
@Embeddable
public class WorkProcessQtyOutput implements ValueObject, Cloneable {

    private long outputQty;

    private long unqualifiedQty;

    private String reason;

    private WorkProcessQtyOutput(){}

    private WorkProcessQtyOutput(long outputQty,
                                 long unqualifiedQty,
                                 String reason){
        this.outputQty = outputQty;
        this.unqualifiedQty = unqualifiedQty;
        this.reason = reason;
    }

    public static WorkProcessQtyOutput create(long outputQty,
                                              long unqualifiedQty,
                                              String reason){
        return new WorkProcessQtyOutput(outputQty,unqualifiedQty,reason);
    }

    public WorkProcessQtyOutput changeOutputQty(long outputQty) {
        this.outputQty = outputQty;
        return new WorkProcessQtyOutput(outputQty,unqualifiedQty,reason);
    }

    public WorkProcessQtyOutput changeUnqualifiedQty(long unqualifiedQty) {
        this.unqualifiedQty = unqualifiedQty;
        return new WorkProcessQtyOutput(outputQty,unqualifiedQty,reason);
    }

    public WorkProcessQtyOutput changeReason(String reason){
        this.reason = reason;
        return new WorkProcessQtyOutput(outputQty,unqualifiedQty,reason);
    }

    public long getOutputQty() {
        return outputQty;
    }

    public long getUnqualifiedQty() {
        return unqualifiedQty;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkProcessQtyOutput)) return false;
        WorkProcessQtyOutput that = (WorkProcessQtyOutput) o;
        return outputQty == that.outputQty && unqualifiedQty == that.unqualifiedQty && Objects.equals(reason, that.reason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(outputQty, unqualifiedQty, reason);
    }

    @Override
    public WorkProcessQtyOutput clone() {
        try {
            WorkProcessQtyOutput clone = (WorkProcessQtyOutput) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
