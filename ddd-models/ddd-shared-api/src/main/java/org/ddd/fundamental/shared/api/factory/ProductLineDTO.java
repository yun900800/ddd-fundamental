package org.ddd.fundamental.shared.api.factory;

import org.ddd.fundamental.core.AbstractDTO;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.factory.ProductionLineId;
import org.ddd.fundamental.factory.value.ProductionLineValue;

import java.util.ArrayList;
import java.util.List;


public class ProductLineDTO extends AbstractDTO<ProductionLineId> {


    private ProductionLineValue lineInfo;

    private List<EquipmentId> equipmentIds;

    private List<WorkStationDTO> workStations;

    @SuppressWarnings("unused")
    protected ProductLineDTO(){}

    protected ProductLineDTO(ProductionLineId id,ProductionLineValue lineInfo,
                             List<EquipmentId> equipmentIds,
                             List<WorkStationDTO> workStations){
        super(id);
        this.equipmentIds = equipmentIds;
        this.lineInfo = lineInfo;
        this.workStations = workStations;
    }

    public static ProductLineDTO create(ProductionLineId id,ProductionLineValue lineInfo,
                                        List<EquipmentId> equipmentIds,
                                        List<WorkStationDTO> workStations){
        return new ProductLineDTO(id,lineInfo,equipmentIds,workStations);
    }

    public ProductionLineValue getLineInfo() {
        return lineInfo.clone();
    }

    public List<EquipmentId> getEquipmentIds() {
        return new ArrayList<>(
                equipmentIds
        );
    }

    public List<WorkStationDTO> getWorkStations() {
        return new ArrayList<>(workStations);
    }

    @Override
    public ProductionLineId id() {
        return super.id;
    }

    @Override
    public String toString() {
        return "ProductLineDTO{" +
                "lineInfo=" + lineInfo +
                ", equipmentIds=" + equipmentIds +
                ", workStations=" + workStations +
                ", id=" + id +
                '}';
    }
}
