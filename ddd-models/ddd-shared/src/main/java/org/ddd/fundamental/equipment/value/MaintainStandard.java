package org.ddd.fundamental.equipment.value;

import org.ddd.fundamental.core.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.Date;
import java.util.Objects;

@Embeddable
@MappedSuperclass
public class MaintainStandard implements ValueObject,Cloneable {
    /**
     * 维护规则
     */
    private String maintainRule;

    private Date workTime;

    @SuppressWarnings("unused")
    private MaintainStandard() {
    }

    private MaintainStandard(String maintainRule,Date workTime){
        this.maintainRule = maintainRule;
        this.workTime = workTime;
    }

    public static MaintainStandard create(String maintainRule,Date workTime){
        return new MaintainStandard(maintainRule,workTime);
    }

    public String getMaintainRule() {
        return maintainRule;
    }

    public Date getWorkTime() {
        return workTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MaintainStandard)) return false;
        MaintainStandard that = (MaintainStandard) o;
        return Objects.equals(maintainRule, that.maintainRule) && Objects.equals(workTime, that.workTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maintainRule, workTime);
    }

    @Override
    public MaintainStandard clone() {
        try {
            MaintainStandard clone = (MaintainStandard) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public String toString() {
        return "MaintainStandard{" +
                "maintainRule='" + maintainRule + '\'' +
                ", workTime=" + workTime +
                '}';
    }
}
