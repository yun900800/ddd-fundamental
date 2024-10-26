package org.ddd.fundamental.equipment.domain.model;

import org.ddd.fundamental.core.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.Date;
import java.util.Objects;


@Embeddable
@MappedSuperclass
public class MaintainStandard implements ValueObject {
    /**
     * 维护规则
     */
    private String maintainRule;

    private Date workTime;

    @SuppressWarnings("unused")
    MaintainStandard() {
    }

    public MaintainStandard(String maintainRule,Date workTime){
        this.maintainRule = maintainRule;
        this.workTime = workTime;
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
}
