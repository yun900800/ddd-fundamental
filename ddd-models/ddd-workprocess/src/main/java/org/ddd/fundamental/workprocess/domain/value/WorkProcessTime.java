package org.ddd.fundamental.workprocess.domain.value;

import org.ddd.fundamental.day.range.DateRange;
import org.ddd.fundamental.day.range.DateRangeValue;

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
    private DateRangeValue transferTime;


    private WorkProcessTime(DateRangeValue waitTimeRange,
                            DateRangeValue setTime,
                            DateRangeValue workTime,
                            DateRangeValue offlineTime,
                            DateRangeValue transferTime
                            ){
        super(waitTimeRange,setTime,workTime, offlineTime);
        this.transferTime = transferTime;
    }

    public static WorkProcessTime create(DateRangeValue waitTimeRange,
                                         DateRangeValue setTime,
                                         DateRangeValue workTime,
                                         DateRangeValue offlineTime,
                                         DateRangeValue transferTime
                                         ){
        return new WorkProcessTime(waitTimeRange,setTime,workTime,offlineTime,transferTime);
    }

    public WorkProcessTime startTransferTime() {
        this.transferTime = DateRangeValue.start();
        return this;
    }

    public WorkProcessCycle endTransferTime(String reason){
        this.transferTime.finish(reason);
        return this;
    }

    public DateRangeValue getTransferTime() {
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
