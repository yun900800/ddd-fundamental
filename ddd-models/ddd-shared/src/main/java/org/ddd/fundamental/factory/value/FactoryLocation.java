package org.ddd.fundamental.factory.value;

import org.ddd.fundamental.core.ValueObject;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.factory.MachineShopId;
import org.ddd.fundamental.factory.ProductionLineId;
import org.ddd.fundamental.factory.WorkStationId;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;

@Embeddable
@MappedSuperclass
public class FactoryLocation implements ValueObject {

    private String locationDesc;

//    todo 警告，这里的MachineShopId没有jpa的注解，因此在生成源模型的时候不会生成对应的属性
    // 如果需要这个属性需要MachineShopId添加注解
    private MachineShopId machineShopId;

    private ProductionLineId productionLineId;

    private WorkStationId workStationId;

    private EquipmentId equipmentId;

    @SuppressWarnings("unused")
    FactoryLocation(){
    }

    private FactoryLocation(MachineShopId machineShopId,
                           ProductionLineId lineId,
                            EquipmentId equipmentId,
                            WorkStationId workStationId){
        this.machineShopId = machineShopId;
        this.productionLineId = lineId;
        this.equipmentId = equipmentId;
        this.workStationId = workStationId;
    }

    interface LocationDescStep {
        MachineShopStep locationDesc(String locationDesc);
    }

    interface MachineShopStep{
        ProductionLineStep machineShopId(MachineShopId machineShopId);
    }

    interface ProductionLineStep {
        LastStep productionLineId(ProductionLineId lineId);
    }
    public interface  LastStep {
        buildStep workStationId(WorkStationId stationId);

        buildStep equipmentId(EquipmentId equipmentId);
    }


    interface buildStep{
        FactoryLocation build();
    }

    public static LocationDescStep newBuilder() {
        return new Builder();
    }

    private static class Builder implements LocationDescStep,
            MachineShopStep, ProductionLineStep, LastStep, buildStep{

        private String locationDesc;
        private MachineShopId machineShopId;

        private ProductionLineId productionLineId;

        private WorkStationId workStationId;

        private EquipmentId equipmentId;

        private Builder() {
        }

        public FactoryLocation build(){
            FactoryLocation factoryLocation = new FactoryLocation();
            factoryLocation.machineShopId = machineShopId;
            factoryLocation.locationDesc = locationDesc;
            factoryLocation.productionLineId = productionLineId;
            if (equipmentId != null) {
                factoryLocation.equipmentId = equipmentId;
            }
            if (workStationId != null) {
                factoryLocation.workStationId = workStationId;
            }
            return factoryLocation;
        }

        @Override
        public MachineShopStep locationDesc(String locationDesc) {
            this.locationDesc = locationDesc;
            return this;
        }

        @Override
        public ProductionLineStep machineShopId(MachineShopId machineShopId) {
            this.machineShopId = machineShopId;
            return this;
        }

        @Override
        public LastStep productionLineId(ProductionLineId lineId) {
            this.productionLineId = productionLineId;
            return this;
        }

        @Override
        public buildStep workStationId(WorkStationId stationId) {
            this.workStationId = stationId;
            return this;
        }

        @Override
        public buildStep equipmentId(EquipmentId equipmentId) {
            this.equipmentId = equipmentId;
            return this;
        }
    }

    public String getLocationDesc() {
        return locationDesc;
    }

    public MachineShopId getMachineShopId() {
        return machineShopId;
    }

    public ProductionLineId getProductionLineId() {
        return productionLineId;
    }

    public WorkStationId getWorkStationId() {
        return workStationId;
    }

    public EquipmentId getEquipmentId() {
        return equipmentId;
    }
}
