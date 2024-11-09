package org.ddd.fundamental.workorder.client;

import org.ddd.fundamental.shared.api.material.MaterialDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "material-client", url = "http://localhost:9001")
public interface MaterialClient {

    @GetMapping("/material/materials")
    List<MaterialDTO> materials();

}
