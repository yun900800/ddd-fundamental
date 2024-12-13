package org.ddd.fundamental.material.rest;

import org.ddd.fundamental.material.application.command.MaterialCommandService;
import org.ddd.fundamental.material.application.query.MaterialQueryService;
import org.ddd.fundamental.material.domain.model.Material;
import org.ddd.fundamental.shared.api.material.MaterialDTO;
import org.ddd.fundamental.material.value.MaterialType;
import org.ddd.fundamental.shared.api.material.MaterialRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MaterialRest {

    @Autowired
    private MaterialQueryService application;

    @Autowired
    private MaterialCommandService commandService;

    @RequestMapping("/material/materials")
    public List<MaterialDTO> materials() {
        return application.materials();
    }

    @RequestMapping("/material/materialsByIds")
    public List<MaterialDTO> materialsByIds(@RequestBody List<String> ids){
        return application.materialsByIds(ids);
    }

    @RequestMapping("/material/materialsByMaterialType")
    public List<MaterialDTO> materialsByMaterialType(@RequestBody MaterialType materialType){
        return application.materialsByMaterialType(materialType);
    }

    @PostMapping("/material/add_material")
    public void addMaterial(@RequestBody MaterialRequest request){
        commandService.addMaterial(request);
    }
}
