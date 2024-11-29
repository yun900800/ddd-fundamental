package org.ddd.fundamental.shared.api.factory;

import org.ddd.fundamental.core.AbstractDTO;
import org.ddd.fundamental.factory.MachineShopId;
import org.ddd.fundamental.factory.value.MachineShopValueObject;

public class MachineShopDTO extends AbstractDTO<MachineShopId> {

    private MachineShopValueObject machineShopValue;

    @SuppressWarnings("unused")
    protected MachineShopDTO(){}

    private MachineShopDTO(MachineShopId id,MachineShopValueObject machineShopValue){
        super(id);
        this.machineShopValue = machineShopValue;
    }

    public static MachineShopDTO create(MachineShopId id,
                                        MachineShopValueObject machineShopValue){
        return new MachineShopDTO(id, machineShopValue);
    }


    public MachineShopValueObject getMachineShopValue() {
        return machineShopValue.clone();
    }

    @Override
    public MachineShopId id() {
        return super.id;
    }

    @Override
    public String toString() {
        return "MachineShopDTO{" +
                "machineShopValue=" + machineShopValue +
                ", id=" + id() +
                '}';
    }
}
