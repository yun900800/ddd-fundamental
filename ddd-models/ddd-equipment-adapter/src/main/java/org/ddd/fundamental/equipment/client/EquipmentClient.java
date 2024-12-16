package org.ddd.fundamental.equipment.client;

import org.ddd.fundamental.shared.api.equipment.EquipmentDTO;
import org.ddd.fundamental.shared.api.equipment.ToolingDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "equipment-client", url = "http://localhost:9004",
        fallbackFactory = EquipmentFactoryClientFallback.class)
public interface EquipmentClient {
    @GetMapping("/equipment/equipments")
    List<EquipmentDTO> equipments();

    @GetMapping("/equipment/toolingList")
    List<ToolingDTO> toolingList();
}
