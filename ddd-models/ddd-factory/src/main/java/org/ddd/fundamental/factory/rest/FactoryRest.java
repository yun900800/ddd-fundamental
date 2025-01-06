package org.ddd.fundamental.factory.rest;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.factory.MachineShopId;
import org.ddd.fundamental.factory.ProductionLineId;
import org.ddd.fundamental.factory.WorkStationId;
import org.ddd.fundamental.factory.application.command.FactoryCommandService;
import org.ddd.fundamental.factory.application.query.FactoryQueryService;
import org.ddd.fundamental.shared.api.factory.CalendarTypeDTO;
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

    @RequestMapping("/calendar/calendar-types")
    public List<CalendarTypeDTO> calendarTypes(){
        return application.calendarTypes();
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

    @PostMapping("/factory/change_lineInfo/{lineId}")
    public void changeProductLineInfo(@PathVariable String lineId,
                                      @RequestBody ChangeableInfo lineInfo){
        commandService.changeProductLineInfo(new ProductionLineId(lineId),lineInfo);
    }

    @PostMapping("/factory/add_equipment_to_line/{lineId}/{equipmentId}")
    public void addEquipmentIdToLine(@PathVariable String lineId,
                                     @PathVariable String equipmentId){
        commandService.addEquipmentIdToLine(
                new ProductionLineId(lineId),
                new EquipmentId(equipmentId)
        );
    }

    @PostMapping("/factory/add_machineShop")
    public void addMachineShop(@RequestBody MachineShopDTO shopDTO){
        commandService.addMachineShop(shopDTO);
    }

    @PostMapping("/factory/change_machineShop/{machineShopId}")
    public void changeMachineShop(@PathVariable String machineShopId,
                                  @RequestBody ChangeableInfo shopInfo){
        commandService.changeMachineShop(new MachineShopId(machineShopId),shopInfo);
    }

    @PostMapping("/factory/add_line_to_machine/{shopId}/{lineId}")
    public void addLineToMachine(@PathVariable String shopId,
                                 @PathVariable String lineId){
        commandService.addLineToMachine(new MachineShopId(shopId),
                new ProductionLineId(lineId));
    }

    @PostMapping("/factory/remove_line_from_machine/{shopId}/{lineId}")
    public void removeLineFromMachine(@PathVariable String shopId,
                                 @PathVariable String lineId){
        commandService.removeLineFromMachine(new MachineShopId(shopId),
                new ProductionLineId(lineId));
    }

    @PostMapping("/factory/remove_machine/{shopId}")
    public void deleteMachine(@PathVariable String shopId){
        commandService.deleteMachine(new MachineShopId(shopId));
    }

}
