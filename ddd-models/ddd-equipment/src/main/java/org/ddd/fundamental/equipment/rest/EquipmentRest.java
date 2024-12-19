package org.ddd.fundamental.equipment.rest;

import org.ddd.fundamental.equipment.application.command.EquipmentCommandService;
import org.ddd.fundamental.equipment.application.query.EquipmentQueryService;
import org.ddd.fundamental.shared.api.equipment.EquipmentDTO;
import org.ddd.fundamental.shared.api.equipment.ToolingDTO;
import org.springframework.beans.factory.annotation.Autowired;
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
}
