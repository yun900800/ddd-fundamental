package org.ddd.fundamental.factory.application.query;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.factory.MachineShopId;
import org.ddd.fundamental.factory.ProductionLineId;
import org.ddd.fundamental.factory.WorkStationId;
import org.ddd.fundamental.factory.domain.model.MachineShop;
import org.ddd.fundamental.factory.domain.model.ProductionLine;
import org.ddd.fundamental.factory.domain.model.WorkStation;
import org.ddd.fundamental.factory.domain.repository.MachineShopRepository;
import org.ddd.fundamental.factory.domain.repository.ProductionLineRepository;
import org.ddd.fundamental.factory.domain.repository.WorkStationRepository;
import org.ddd.fundamental.factory.helper.FactoryHelper;
import org.ddd.fundamental.factory.utils.SpringTransactionStatistics;
import org.ddd.fundamental.redis.config.RedisStoreManager;
import org.ddd.fundamental.shared.api.factory.MachineShopDTO;
import org.ddd.fundamental.shared.api.factory.ProductLineDTO;
import org.ddd.fundamental.shared.api.factory.WorkStationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(readOnly = true)
public class FactoryQueryService {

    @Autowired
    private MachineShopRepository machineShopRepository;

    @Autowired
    private ProductionLineRepository productionLineRepository;

    @Autowired
    private WorkStationRepository workStationRepository;

    @Autowired
    private RedisStoreManager manager;

    @Transactional
    public List<MachineShopDTO> machineShops(){
        List<MachineShopDTO> machineShopDTOS = manager.queryAllData(MachineShopDTO.class);
        if (!CollectionUtils.isEmpty(machineShopDTOS)){
            return machineShopDTOS;
        }
        simulateCostCall();
        List<MachineShop> machineShopList = machineShopRepository.findAll();
        if (CollectionUtils.isEmpty(machineShopList)) {
            return new ArrayList<>();
        }
        machineShopDTOS =
        machineShopList.stream().map(v->MachineShopDTO.create(v.id(),v.getMachineShop()))
                .collect(Collectors.toList());
        return machineShopDTOS;
    }

    private void simulateCostCall(){
        long startNanos = System.nanoTime();
        FactoryHelper.simulateCostCall();
//        SpringTransactionStatistics.report().fxRateTime(
//                System.nanoTime() - startNanos
//        );
    }

    public List<MachineShopDTO> machineShopsByIds(List<MachineShopId> ids){
        List<MachineShopDTO> machineShopDTOS = manager.fetchDataListFromCache(ids,MachineShopDTO.class);
        if (!CollectionUtils.isEmpty(machineShopDTOS)) {
            return machineShopDTOS;
        }
        List<MachineShop> machineShops = machineShopRepository.findByIdIn(ids);
        if (CollectionUtils.isEmpty(machineShops)) {
            return new ArrayList<>();
        }
        return machineShops.stream().map(v->MachineShopDTO.create(v.id(),v.getMachineShop()))
                .collect(Collectors.toList());
    }

    public List<ProductLineDTO> productLines(){
        List<ProductLineDTO>  productLineDTOS = manager.queryAllData(ProductLineDTO.class);
        if (!CollectionUtils.isEmpty(productLineDTOS)){
            return productLineDTOS;
        }

        List<ProductionLine> productionLines = productionLineRepository.findAll();
        if (CollectionUtils.isEmpty(productionLines)) {
            return new ArrayList<>();
        }
        productLineDTOS = productionLines.stream().map(v->ProductLineDTO.create(
                v.id(),v.getLine(), v.getEquipmentIds(), v.getWorkStations().stream()
                        .map(u-> WorkStationDTO.create(u.id(), u.getWorkStation()))
                        .collect(Collectors.toList())
        )).collect(Collectors.toList());
        return productLineDTOS;
    }

    public List<ProductLineDTO> productLinesByIds(List<ProductionLineId> ids){
        List<ProductLineDTO> lineDTOS = manager.fetchDataListFromCache(ids,ProductLineDTO.class);
        if (!CollectionUtils.isEmpty(lineDTOS)) {
            return lineDTOS;
        }

        List<ProductionLine> productionLines = productionLineRepository.findByIdIn(ids);
        if (CollectionUtils.isEmpty(productionLines)) {
            return new ArrayList<>();
        }
        return productionLines.stream().map(v->ProductLineDTO.create(
                v.id(),v.getLine(), v.getEquipmentIds(), v.getWorkStations().stream()
                        .map(u-> WorkStationDTO.create(u.id(), u.getWorkStation()))
                        .collect(Collectors.toList())
        )).collect(Collectors.toList());

    }

    public List<WorkStationDTO> findByLineId(ProductionLineId id){
        List<WorkStation> workStations = workStationRepository.findByLineId(id);
        if (CollectionUtils.isEmpty(workStations)){
            return new ArrayList<>();
        }
        return workStations.stream().map(
                u-> WorkStationDTO.create(u.id(), u.getWorkStation())
        ).collect(Collectors.toList());
    }

    /**
     * 获取产线数据
     * @param lineId
     * @return
     */
    public ProductionLine findProductionLineById(ProductionLineId lineId){
        ProductionLine line = productionLineRepository.findById(lineId).orElse(null);
        if (null == line) {
            String msg = "id:%s 对应的ProductionLine 不存在";
            throw new RuntimeException(String.format(msg,lineId.toUUID()));
        }
        return line;
    }

    /**
     * 获取工位信息
     * @param stationId
     * @return
     */
    public WorkStation findWorkStationById(WorkStationId stationId){
        WorkStation station = workStationRepository.findById(stationId).orElse(null);
        if (null == station) {
            String msg = "id:%s 对应的WorkStation 不存在";
            throw new RuntimeException(String.format(msg,stationId.toUUID()));
        }
        return station;
    }

    /**
     * 获取车间信息
     * @param shopId
     * @return
     */
    public MachineShop findMachineShopById(MachineShopId shopId){
        MachineShop machineShop = machineShopRepository.findById(shopId).orElse(null);
        if (null == machineShop) {
            String msg = "id:%s 对应的MachineShop 不存在";
            throw new RuntimeException(String.format(msg,shopId.toUUID()));
        }
        return machineShop;
    }

}
