package org.ddd.fundamental.workprocess.client;

import org.ddd.fundamental.shared.api.equipment.EquipmentDTO;
import org.ddd.fundamental.shared.api.equipment.ToolingDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "equipment-client1", url = "http://localhost:9004")
public interface EquipmentClient {

    @GetMapping("/equipment/equipments")
    List<EquipmentDTO> equipments();

    @GetMapping("/equipment/toolingList")
    List<ToolingDTO> toolingList();
}
