package org.ddd.fundamental.schedule.domain.model;

import org.ddd.fundamental.core.AbstractAggregateRoot;
import org.ddd.fundamental.schedule.value.MasterProductScheduleValue;
import org.ddd.fundamental.schedule.value.ScheduleId;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table( name = "master_product_schedule")
public class MasterProductSchedule extends AbstractAggregateRoot<ScheduleId> {

    private MasterProductScheduleValue masterProductSchedule;

    @SuppressWarnings("unused")
    protected MasterProductSchedule(){}

    private MasterProductSchedule(MasterProductScheduleValue masterProductSchedule){
        super(ScheduleId.randomId(ScheduleId.class));
        this.masterProductSchedule = masterProductSchedule;
    }

    public static MasterProductSchedule create(MasterProductScheduleValue masterProductSchedule){
        return new MasterProductSchedule(masterProductSchedule);
    }

    public MasterProductScheduleValue getMasterProductSchedule() {
        return masterProductSchedule.clone();
    }

    @Override
    public long created() {
        return 0;
    }
}
