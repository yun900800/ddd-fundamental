package org.ddd.fundamental.factory.domain.model;

import org.ddd.fundamental.core.AbstractEntity;
import org.ddd.fundamental.factory.ProductionLineId;
import org.ddd.fundamental.factory.WorkStationId;
import org.ddd.fundamental.factory.value.WorkStationValueObject;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;


@Entity
@Table(name = "f_work_station")
public class WorkStation extends AbstractEntity<WorkStationId> {

    @Embedded
    private WorkStationValueObject workStation;

    @SuppressWarnings("unused")
    private WorkStation(){}

    public WorkStation(WorkStationValueObject workStation){
        super(WorkStationId.randomId(WorkStationId.class));
        this.workStation = workStation;
    }

    public WorkStationValueObject getWorkStation() {
        return workStation.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkStation)) return false;
        if (!super.equals(o)) return false;
        WorkStation that = (WorkStation) o;
        return Objects.equals(id(), that.id());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id());
    }

    @Override
    public String toString() {
        return "WorkStation{" +
                "workStation=" + workStation +
                '}';
    }

    @Override
    public long created() {
        return 0;
    }
}
