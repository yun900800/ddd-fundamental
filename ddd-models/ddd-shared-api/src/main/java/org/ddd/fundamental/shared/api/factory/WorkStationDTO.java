package org.ddd.fundamental.shared.api.factory;

import org.ddd.fundamental.core.AbstractDTO;
import org.ddd.fundamental.factory.WorkStationId;
import org.ddd.fundamental.factory.value.WorkStationValueObject;

public class WorkStationDTO extends AbstractDTO<WorkStationId> {

    private WorkStationValueObject workStation;

    @SuppressWarnings("unused")
    protected WorkStationDTO(){}

    protected WorkStationDTO(WorkStationId id,
                             WorkStationValueObject workStation){
        super(id);
        this.workStation = workStation;
    }

    public static WorkStationDTO create(WorkStationId id,
                                        WorkStationValueObject workStationValue){
        return new WorkStationDTO(id,workStationValue);
    }

    public WorkStationValueObject getWorkStation() {
        return workStation.clone();
    }

    @Override
    public WorkStationId id() {
        return super.id;
    }

    @Override
    public String toString() {
        return "WorkStationDTO{" +
                "workStation=" + workStation +
                ", id=" + id +
                '}';
    }
}
