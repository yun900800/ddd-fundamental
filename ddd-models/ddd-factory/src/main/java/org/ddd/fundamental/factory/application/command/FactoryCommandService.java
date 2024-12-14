package org.ddd.fundamental.factory.application.command;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.factory.ProductionLineId;
import org.ddd.fundamental.factory.WorkStationId;
import org.ddd.fundamental.factory.domain.model.ProductionLine;
import org.ddd.fundamental.factory.domain.model.WorkStation;
import org.ddd.fundamental.factory.domain.repository.MachineShopRepository;
import org.ddd.fundamental.factory.domain.repository.ProductionLineRepository;
import org.ddd.fundamental.factory.domain.repository.WorkStationRepository;
import org.ddd.fundamental.factory.value.ProductionLineValue;
import org.ddd.fundamental.shared.api.factory.WorkStationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Slf4j
@Transactional
public class FactoryCommandService {

    private final MachineShopRepository machineShopRepository;

    private final ProductionLineRepository productionLineRepository;

    private final WorkStationRepository workStationRepository;

    @Autowired
    public FactoryCommandService(
            MachineShopRepository machineShopRepository,
            ProductionLineRepository productionLineRepository,
            WorkStationRepository workStationRepository){
        this.machineShopRepository = machineShopRepository;
        this.productionLineRepository = productionLineRepository;
        this.workStationRepository = workStationRepository;
    }

    /**
     * 增加一个产线信息,
     * 可以包含工位
     * @param lineValue
     * @param workStations
     */
    public void addProductLine(ProductionLineValue lineValue,
                               List<EquipmentId> equipmentIds,
                               List<WorkStationDTO> workStations){
        ProductionLine productionLine = new ProductionLine(lineValue);
        if (!CollectionUtils.isEmpty(workStations)) {
            for (WorkStationDTO workStationDTO: workStations) {
                productionLine.addWorkStation(new WorkStation(
                        workStationDTO.getWorkStation()
                ));
            }
        }
        if (!CollectionUtils.isEmpty(equipmentIds)) {
            for (EquipmentId id: equipmentIds) {
                productionLine.addEquipment(id);
            }
        }
        productionLineRepository.persist(productionLine);
    }

    /**
     * 从产线中移除工位
     * @param workStationId
     * @param lineId
     */
    public void deleteWorkStation(WorkStationId workStationId, ProductionLineId lineId){
        ProductionLine line = productionLineRepository.findById(lineId).orElse(null);
        if (null == line) {
            return;
        }
        WorkStation station = workStationRepository.findById(workStationId).orElse(null);
        if (null == station) {
            return;
        }
        line.removeWorkStation(station);
    }
}
