package org.ddd.fundamental.factory.application;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.factory.domain.model.MachineShop;
import org.ddd.fundamental.factory.domain.model.ProductionLine;
import org.ddd.fundamental.factory.domain.repository.MachineShopRepository;
import org.ddd.fundamental.factory.domain.repository.ProductionLineRepository;
import org.ddd.fundamental.shared.api.factory.MachineShopDTO;
import org.ddd.fundamental.shared.api.factory.ProductLineDTO;
import org.ddd.fundamental.shared.api.factory.WorkStationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(readOnly = true)
public class FactoryApplication {

    @Autowired
    private MachineShopRepository machineShopRepository;

    @Autowired
    private ProductionLineRepository productionLineRepository;

    public List<MachineShopDTO> machineShops(){
        List<MachineShop> machineShopList = machineShopRepository.findAll();
        if (CollectionUtils.isEmpty(machineShopList)) {
            return new ArrayList<>();
        }
        return machineShopList.stream().map(v->MachineShopDTO.create(v.id(),v.getMachineShop()))
                .collect(Collectors.toList());
    }

    public List<ProductLineDTO> productLines(){
        List<ProductionLine> productionLines = productionLineRepository.findAll();
        if (CollectionUtils.isEmpty(productionLines)) {
            return new ArrayList<>();
        }
        return productionLines.stream().map(v->ProductLineDTO.create(
                v.id(),v.getLine(), v.getEquipmentIds(), v.getWorkStations().stream()
                        .map(u-> WorkStationDTO.create(u.id(), u.getWorkStation()))
                        .collect(Collectors.toList())
        )).collect(Collectors.toList());
    }

}
