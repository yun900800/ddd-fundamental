package org.ddd.fundamental.equipment.rest;

import org.ddd.fundamental.equipment.application.command.EquipmentCommandService;
import org.ddd.fundamental.equipment.application.query.EquipmentQueryService;
import org.ddd.fundamental.equipment.value.EquipmentRPAccountValue;
import org.ddd.fundamental.equipment.value.RPAccountId;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.shared.api.equipment.EquipmentDTO;
import org.ddd.fundamental.shared.api.equipment.EquipmentRequest;
import org.ddd.fundamental.shared.api.equipment.RPAccountDTO;
import org.ddd.fundamental.shared.api.equipment.ToolingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EquipmentRest {

    @Autowired
    private EquipmentCommandService commandService;

    private EquipmentQueryService queryService;

    @RequestMapping("/equipment/equipments")
    public List<EquipmentDTO> equipments() {
        return queryService.equipments();
    }

    @RequestMapping("/equipment/toolingList")
    public List<ToolingDTO> toolingList() {
        return queryService.toolingList();
    }

    @RequestMapping("/equipment/rpAccountList")
    public List<RPAccountDTO> rpAccountList(){
        return queryService.rpAccountList();
    }

    @PostMapping("/equipment/create_equipment")
    public void createEquipment(@RequestBody EquipmentRequest request){
        commandService.createEquipment(request.getMaster(),
                request.getModel(),request.getType(),
                request.getResource(),request.getResourceType());
    }

    @PostMapping("/equipment/add_accounts_to_equipment/{equipmentId}")
    public void addRpAccountsToEquipment(@PathVariable String equipmentId,
                                          @RequestBody List<RPAccountId> accountIds){
        commandService.addRpAccountsToEquipment(new EquipmentId(equipmentId),accountIds);
    }

    @PostMapping("/equipment/add_account_to_equipment/{equipmentId}/{accountId}")
    public void addRpAccountToEquipment(@PathVariable String equipmentId,
                                        @PathVariable String accountId,
                                        @RequestBody EquipmentRPAccountValue value){
        commandService.addRpAccountToEquipment(new EquipmentId(equipmentId),
                new RPAccountId(accountId),value);
    }

    @PostMapping("/equipment/add_tooling_to_equipment/{equipmentId}/{toolingId}")
    public void addToolingToEquipment(@PathVariable String toolingId,
                                      @PathVariable String equipmentId){
        commandService.addToolingToEquipment(new EquipmentId(toolingId),new EquipmentId(equipmentId));
    }


}
