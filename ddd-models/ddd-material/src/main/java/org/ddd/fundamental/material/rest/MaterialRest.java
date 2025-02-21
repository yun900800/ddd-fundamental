package org.ddd.fundamental.material.rest;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.material.MaterialMaster;
import org.ddd.fundamental.redis.cache.LoadingCacheStore;
import org.ddd.fundamental.material.application.command.MaterialCommandService;
import org.ddd.fundamental.material.application.query.MaterialQueryService;
import org.ddd.fundamental.material.domain.value.ControlProps;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.shared.api.material.MaterialDTO;
import org.ddd.fundamental.material.value.MaterialType;
import org.ddd.fundamental.shared.api.material.MaterialRequest;
import org.ddd.fundamental.shared.api.material.PropsContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class MaterialRest {

    @Autowired
    private MaterialQueryService application;

    @Autowired
    private MaterialCommandService commandService;

    @Autowired
    private LoadingCacheStore<MaterialDTO> materialDTOLoadingCacheStore;

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

    @PostMapping("/material/change_materialInfo/{id}")
    public void changeMaterialInfo(@RequestBody ChangeableInfo info,
                                   @PathVariable String id){
        //commandService.changeMaterialInfo(info,new MaterialId(id));
    }

    @PostMapping("/material/change_materialMaster/{id}")
    public void changeMaterialMaster(@RequestBody MaterialMaster materialMaster,
                                     @PathVariable String id){
        commandService.changeMaterialMaster(materialMaster,new MaterialId(id));
    }

    @PostMapping("/material/change_materialControl/{id}")
    public void changeMaterialControl(@RequestBody ControlProps controlProps,
                                      @PathVariable String id){
        commandService.changeMaterialControl(controlProps,new MaterialId(id));
    }

    @PostMapping("/material/add_optional_props/{id}")
    public void addOptionalProps(@RequestBody PropsContainer propsContainer,
                                 @PathVariable String id){
        commandService.addOptionalProps(propsContainer,new MaterialId(id));
    }

    @PostMapping("/material/add_optional_character/{id}")
    public void addOptionalCharacter(@RequestBody PropsContainer propsContainer,
                                 @PathVariable String id){
        commandService.addOptionalCharacter(propsContainer,new MaterialId(id));
    }

    @RequestMapping("/material/getMaterialById/{id}")
    public MaterialDTO getMaterialById(@PathVariable String id){
        MaterialDTO materialDTO = materialDTOLoadingCacheStore.get(id);
        if (null != materialDTO) {
            log.info("fetch data by key:{} from Loading cache",id);
            return materialDTO;
        }
        return application.getMaterialById(new MaterialId(id));
    }
}
