package org.ddd.fundamental.factory.rest;

import org.ddd.fundamental.factory.MachineShopId;
import org.ddd.fundamental.factory.ProductionLineId;
import org.ddd.fundamental.factory.WorkStationId;
import org.ddd.fundamental.factory.application.command.FactoryCommandService;
import org.ddd.fundamental.factory.application.query.FactoryQueryService;
import org.ddd.fundamental.shared.api.factory.MachineShopDTO;
import org.ddd.fundamental.shared.api.factory.ProductLineDTO;
import org.ddd.fundamental.shared.api.factory.WorkStationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FactoryRest {

    @Autowired
    private FactoryQueryService application;

    @Autowired
    private FactoryCommandService commandService;

    @RequestMapping("/factory/machine-shops")
    public List<MachineShopDTO> machineShops() {
        return application.machineShops();
    }

    @RequestMapping("/factory/machine-shopsByIds")
    public List<MachineShopDTO> machineShopsByIds(@RequestBody List<String> ids){
        return application.machineShopsByIds(ids.stream().map(v->new MachineShopId(v))
                .collect(Collectors.toList()));
    }

    @RequestMapping("/factory/product-lines")
    public List<ProductLineDTO> productLines() {
        return application.productLines();
    }

    @RequestMapping("/factory/product-linesByIds")
    public List<ProductLineDTO> productLinesByIds(@RequestBody List<String> ids){
        return application.productLinesByIds(ids.stream().map(v->new ProductionLineId(v))
                .collect(Collectors.toList()));
    }

    @PostMapping("/factory/add-line")
    public void addProductLine(@RequestBody ProductLineDTO productLineDTO){
        commandService.addProductLine(productLineDTO.getLineInfo(),
                productLineDTO.getEquipmentIds(),
                productLineDTO.getWorkStations());
    }

    @PostMapping("/factory/delete_work_station/{lineId}/{workStationId}")
    public void deleteWorkStation(@PathVariable String workStationId,
                                  @PathVariable String lineId){
        commandService.deleteWorkStation(new WorkStationId(workStationId),new ProductionLineId(lineId));
    }

    @PostMapping("/factory/delete_line/{lineId}")
    public void deleteProductLine(@PathVariable String lineId){
        commandService.deleteProductLine(new  ProductionLineId(lineId));
    }

    @PostMapping("/factory/update_station/{workStationId}")
    public void updateWorkStation(@PathVariable String workStationId,
                                  @RequestBody WorkStationDTO stationDTO){
        commandService.updateWorkStation(new WorkStationId(workStationId),stationDTO);
    }

}
