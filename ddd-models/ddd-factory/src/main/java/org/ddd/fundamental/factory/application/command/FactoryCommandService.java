package org.ddd.fundamental.factory.application.command;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.factory.MachineShopId;
import org.ddd.fundamental.factory.ProductionLineId;
import org.ddd.fundamental.factory.WorkStationId;
import org.ddd.fundamental.factory.application.query.FactoryQueryService;
import org.ddd.fundamental.factory.domain.model.MachineShop;
import org.ddd.fundamental.factory.domain.model.ProductionLine;
import org.ddd.fundamental.factory.domain.model.WorkStation;
import org.ddd.fundamental.factory.domain.repository.MachineShopRepository;
import org.ddd.fundamental.factory.domain.repository.ProductionLineRepository;
import org.ddd.fundamental.factory.domain.repository.WorkStationRepository;
import org.ddd.fundamental.factory.value.ProductionLineValue;
import org.ddd.fundamental.shared.api.factory.MachineShopDTO;
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

    private final FactoryQueryService queryService;


    @Autowired
    public FactoryCommandService(
            MachineShopRepository machineShopRepository,
            ProductionLineRepository productionLineRepository,
            WorkStationRepository workStationRepository,
            FactoryQueryService queryService){
        this.machineShopRepository = machineShopRepository;
        this.productionLineRepository = productionLineRepository;
        this.workStationRepository = workStationRepository;
        this.queryService = queryService;
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
        ProductionLine line = queryService.findProductionLineById(lineId);
        WorkStation station = queryService.findWorkStationById(workStationId);
        line.removeWorkStation(station);
    }

    /**
     * 更新工位信息,这个方法没有使用领域模型的聚合进行修改
     *
     * @param workStationId
     * @param stationDTO
     */
    public void updateWorkStation(WorkStationId workStationId, WorkStationDTO stationDTO){
        WorkStation station = queryService.findWorkStationById(workStationId);
        station.changeWorkStation(stationDTO.getWorkStation().getWorkStation());
        workStationRepository.save(station);
    }

    /**
     * 修改产线信息
     * @param lineId
     * @param lineInfo
     */
    public void changeProductLineInfo(ProductionLineId lineId, ChangeableInfo lineInfo){
        ProductionLine line = queryService.findProductionLineById(lineId);
        line.changeLineInfo(lineInfo);
    }

    /**
     *
     * @param lineId
     */
    public void deleteProductLine(ProductionLineId lineId){
        productionLineRepository.deleteById(lineId);
    }

    /**
     * 添加设备ID信息到产线
     * @param lineId
     * @param equipmentId
     */
    public void addEquipmentIdToLine(ProductionLineId lineId, EquipmentId equipmentId){
        ProductionLine line = queryService.findProductionLineById(lineId);
        line.addEquipment(equipmentId);
    }

    /**
     * 更新车间信息
     * @param shopId
     * @param shopInfo
     */
    public void changeMachineShop(MachineShopId shopId,
                                  ChangeableInfo shopInfo){
        MachineShop shop = queryService.findMachineShopById(shopId);
        shop.changeMachineShop(shopInfo);
    }

    /**
     * 添加车间信息
     * @param shopDTO
     */
    public void addMachineShop(MachineShopDTO shopDTO){
        MachineShop shop = new MachineShop(
                shopDTO.getMachineShopValue()
        );
        machineShopRepository.persist(shop);
    }

    /**
     * 添加产线到车间
     * @param shopId
     * @param lineId
     */
    public void addLineToMachine(MachineShopId shopId,ProductionLineId lineId){
        MachineShop shop = queryService.findMachineShopById(shopId);
        shop.addLines(lineId);
    }

    /**
     * 从车间移除产线
     * @param shopId
     * @param lineId
     */
    public void removeLineFromMachine(MachineShopId shopId,ProductionLineId lineId){
        MachineShop shop = queryService.findMachineShopById(shopId);
        shop.removeLines(lineId);
    }

    /**
     * 移除车间
     * @param shopId
     */
    public void deleteMachine(MachineShopId shopId){
        machineShopRepository.deleteById(shopId);
    }

}
