package org.ddd.fundamental.material.client;

import org.ddd.fundamental.material.value.MaterialType;
import org.ddd.fundamental.shared.api.material.MaterialDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = "material-client", url = "http://localhost:9001",
        fallbackFactory = MaterialFactoryClientFallback.class)
public interface MaterialClient {
    @RequestMapping("/material/materialsByMaterialType")
    List<MaterialDTO> materialsByMaterialType(MaterialType materialType);

    @GetMapping("/material/materials")
    List<MaterialDTO> materials();

    @RequestMapping("/material/materialsByIds")
    List<MaterialDTO> materialsByIds(@RequestBody List<String> ids);
}
