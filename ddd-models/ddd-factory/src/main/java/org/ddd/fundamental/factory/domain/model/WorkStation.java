package org.ddd.fundamental.factory.domain.model;

import org.ddd.fundamental.core.AbstractEntity;
import org.ddd.fundamental.factory.WorkStationId;
import org.ddd.fundamental.factory.value.WorkStationValueObject;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Table(name = "f_work_station")
public class WorkStation extends AbstractEntity<WorkStationId> {

    @Embedded
    private WorkStationValueObject workStation;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProductionLine line;

    @SuppressWarnings("unused")
    protected WorkStation(){}

    public WorkStation(WorkStationValueObject workStation){
        super(WorkStationId.randomId(WorkStationId.class));
        this.workStation = workStation;
    }

    public void setLine(ProductionLine line) {
        this.line = line;
    }

    public ProductionLine getLine() {
        return line;
    }

    public WorkStationValueObject getWorkStation() {
        return workStation.clone();
    }

    public WorkStation changeName(String name){
        this.workStation = this.workStation.changeName(name);
        return this;
    }

    public WorkStation changeDesc(String desc){
        this.workStation = this.workStation.changeDesc(desc);
        return this;
    }

    public WorkStation enableUse() {
        this.workStation = this.workStation.enableUse();
        return this;
    }

    public WorkStation disableUse(){
        this.workStation = this.workStation.disableUse();
        return this;
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
