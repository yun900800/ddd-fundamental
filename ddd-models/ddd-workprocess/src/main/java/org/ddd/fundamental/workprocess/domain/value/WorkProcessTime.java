package org.ddd.fundamental.workprocess.domain.value;

import org.ddd.fundamental.day.range.DateRange;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.Objects;


/**
 * 工序时间模型
 */
@MappedSuperclass
@Embeddable
public class WorkProcessTime extends WorkProcessCycle implements Cloneable {

    @SuppressWarnings("unused")
    private WorkProcessTime(){
    }


    /**
     * 运输时间
     */
    private DateRange transferTime;


    private WorkProcessTime(DateRange waitTimeRange,
                            DateRange setTime,
                            DateRange workTime,
                            DateRange offlineTime,
                            DateRange transferTime
                            ){
        super(waitTimeRange,setTime,workTime, offlineTime);
        this.transferTime = transferTime;
    }

    public static WorkProcessTime create(DateRange waitTimeRange,
                                         DateRange setTime,
                                         DateRange workTime,
                                         DateRange offlineTime,
                                         DateRange transferTime
                                         ){
        return new WorkProcessTime(waitTimeRange,setTime,workTime,offlineTime,transferTime);
    }

    public DateRange getTransferTime() {
        return transferTime.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkProcessTime)) return false;
        if (!super.equals(o)) return false;
        WorkProcessTime that = (WorkProcessTime) o;
        return Objects.equals(transferTime, that.transferTime) && super.equals(that);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), transferTime);
    }

    @Override
    public String toString() {
        return "WorkProcessTime{" +
                "transferTime=" + transferTime +
                super.toString()+
                '}';
    }

    @Override
    public WorkProcessTime clone() {
        WorkProcessTime clone = (WorkProcessTime) super.clone();
        // TODO: copy mutable state here, so the clone can't change the internals of the original
        return clone;
    }
}
