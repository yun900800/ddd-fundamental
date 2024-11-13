package org.ddd.fundamental.workprocess.client;

import org.ddd.fundamental.shared.api.material.MaterialDTO;
import org.ddd.fundamental.shared.api.material.enums.MaterialType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = "material-client", url = "http://localhost:9001")
public interface MaterialClient {

    @RequestMapping("/material/materialsByMaterialType")
    List<MaterialDTO> materialsByMaterialType(MaterialType materialType);

}
