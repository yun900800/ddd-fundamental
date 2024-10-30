package org.ddd.fundamental.factory.value;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.core.ValueObject;

import javax.persistence.*;
import java.util.Objects;

@MappedSuperclass
@Embeddable
public class WorkStationValueObject implements ValueObject, Cloneable {

    @AttributeOverrides({
            @AttributeOverride(name = "name" , column =@Column( name = "work_station_name", nullable = false)),
            @AttributeOverride(name = "desc" , column =@Column( name = "work_station_desc", nullable = false)),
            @AttributeOverride(name = "isUse" , column =@Column( name = "work_station_is_use", nullable = false))
    })
    private ChangeableInfo workStation;
    @SuppressWarnings("unused")
    private WorkStationValueObject(){
    }

    public WorkStationValueObject(ChangeableInfo info){
        this.workStation = info;
    }

    public ChangeableInfo getWorkStation() {
        return workStation.clone();
    }

    public WorkStationValueObject changeName(String name) {
        this.workStation = this.workStation.changeName(name);
        return this;
    }

    public WorkStationValueObject changeDesc(String desc) {
        this.workStation = this.workStation.changeDesc(desc);
        return this;
    }

    public String name(){
        return this.workStation.getName();
    }

    public String desc(){
        return this.workStation.getDesc();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkStationValueObject)) return false;
        WorkStationValueObject that = (WorkStationValueObject) o;
        return Objects.equals(workStation, that.workStation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workStation);
    }

    @Override
    public String toString() {
        return "WorkStationValueObject{" +
                "workStation=" + workStation +
                '}';
    }

    @Override
    public WorkStationValueObject clone() {
        try {
            WorkStationValueObject clone = (WorkStationValueObject) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
