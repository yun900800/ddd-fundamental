package org.ddd.fundamental.material.schedule;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.material.MaterialMaster;
import org.ddd.fundamental.material.application.query.MaterialQueryService;
import org.ddd.fundamental.material.creator.MaterialAddable;
import org.ddd.fundamental.material.domain.model.Material;
import org.ddd.fundamental.material.helper.MaterialHelper;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.material.value.MaterialType;
import org.ddd.fundamental.shared.api.material.MaterialDTO;
import org.ddd.fundamental.shared.api.material.MaterialRequest;
import org.ddd.fundamental.utils.CollectionUtils;
import org.ddd.fundamental.workprocess.value.WorkProcessBeat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
public class MaterialTemplateClient {

    private final MaterialQueryService materialQueryService;

    private List<MaterialDTO> materialCache;

    private static final String ADD_MATERIAL = "http://localhost:9001/material/add_material";

    private static final String CHANGE_MATERIAL = "http://localhost:9001/material/change_materialInfo/%s";

    @Autowired
    public MaterialTemplateClient(MaterialQueryService materialQueryService){
        this.materialQueryService = materialQueryService;
    }

    @Scheduled(cron = "*/30 * * * * ?")
    public void addMaterial() {
        MaterialType type = CollectionUtils.random(Arrays.asList(MaterialType.values()));
        String name = CollectionUtils.random(MaterialHelper.createMaterial().get(type));
        Map<String,String> requiredMap = MaterialAddable.createRequiredMap();
        Map<String,String> characterMap = MaterialAddable.createCharacterMap();
        MaterialRequest request = MaterialRequest.create(
                MaterialMaster.create(
                        requiredMap.get("code"),
                        name,
                        requiredMap.get("spec"),
                        requiredMap.get("unit")
                ),
                new MaterialId("0"),
                type,
                Set.of("code","spec","unit"),
                Set.of("width","height"),
                requiredMap,
                characterMap
        );
        log.info("url is {}",ADD_MATERIAL);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(ADD_MATERIAL,request,Void.class);
        log.info("add material finished");
    }

    @Scheduled(cron = "*/5 * * * * ?")
    public void changeMaterialInfo() {
        if (org.springframework.util.CollectionUtils.isEmpty(materialCache)){
            this.materialCache = materialQueryService.materials();
        }
        String id = CollectionUtils.random(materialCache).id().toUUID();
        ChangeableInfo info = ChangeableInfo.create(
                "基础物料测试", "测试物料的自动修改",
                true
        );
        String url = String.format(CHANGE_MATERIAL,id);
        log.info("url is {}",url);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(url,info,Void.class);
        log.info("change materialInfo finished");
    }

}
