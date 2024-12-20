package org.ddd.fundamental.equipment.rest;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.day.YearModelValue;
import org.ddd.fundamental.equipment.application.command.EquipmentCommandService;
import org.ddd.fundamental.equipment.application.query.EquipmentQueryService;
import org.ddd.fundamental.equipment.enums.EquipmentType;
import org.ddd.fundamental.equipment.value.EquipmentMaster;
import org.ddd.fundamental.shared.api.equipment.EquipmentDTO;
import org.ddd.fundamental.shared.api.equipment.EquipmentRequest;
import org.ddd.fundamental.shared.api.equipment.RPAccountDTO;
import org.ddd.fundamental.shared.api.equipment.ToolingDTO;
import org.ddd.fundamental.workprocess.enums.ProductResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
