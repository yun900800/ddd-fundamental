package org.ddd.fundamental.workorder.client;

import org.ddd.fundamental.shared.api.optemplate.CraftsmanShipTemplateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = "work-process-client", url = "http://localhost:9003")
public interface WorkProcessClient {
    @RequestMapping("/process/craftsmanShip-templates")
    List<CraftsmanShipTemplateDTO> craftsmanShipTemplates();
}
