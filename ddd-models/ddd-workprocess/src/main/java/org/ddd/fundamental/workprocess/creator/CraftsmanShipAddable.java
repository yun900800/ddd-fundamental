package org.ddd.fundamental.workprocess.creator;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.creator.DataAddable;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.material.value.MaterialType;
import org.ddd.fundamental.redis.config.RedisStoreManager;
import org.ddd.fundamental.shared.api.material.MaterialDTO;
import org.ddd.fundamental.shared.api.optemplate.CraftsmanShipTemplateDTO;
import org.ddd.fundamental.utils.CollectionUtils;
import org.ddd.fundamental.workprocess.client.MaterialClient;
import org.ddd.fundamental.workprocess.domain.model.CraftsmanShipTemplate;
import org.ddd.fundamental.workprocess.domain.repository.CraftsmanShipRepository;
import org.ddd.fundamental.workprocess.domain.repository.WorkProcessTemplateRepository;
import org.ddd.fundamental.workprocess.value.WorkProcessTemplateId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
@Slf4j
@Order(2)
public class CraftsmanShipAddable implements DataAddable {

    private final CraftsmanShipRepository craftsmanShipRepository;

    private final RedisStoreManager manager;

    private final MaterialClient client;

    private final WorkProcessTemplateAddable templateAddable;

    private final WorkProcessTemplateRepository templateRepository;

    private List<CraftsmanShipTemplate> craftsmanShipTemplates;

    @Autowired
    public CraftsmanShipAddable(CraftsmanShipRepository craftsmanShipRepository,
                                  RedisStoreManager manager,
                                MaterialClient client,
                                WorkProcessTemplateAddable templateAddable,
                                WorkProcessTemplateRepository templateRepository){
        this.craftsmanShipRepository = craftsmanShipRepository;
        this.manager = manager;
        this.client = client;
        this.templateAddable = templateAddable;
        this.templateRepository = templateRepository;
    }

    private List<WorkProcessTemplateId> randomTemplateIds(List<WorkProcessTemplateId> templateIds) {
        int size = templateIds.size();
        int randomSize = new Random().nextInt(size/2);
        while (randomSize == 0 || randomSize >= 5){
            randomSize = new Random().nextInt(size/2);
        }
        List<WorkProcessTemplateId> results = new ArrayList<>();
        for (int i = 0 ; i < randomSize; i++) {
            results.add(CollectionUtils.random(templateIds));
        }
        return results;
    }

    private CraftsmanShipTemplate createCraftsmanShipTemplate(MaterialId materialId) {
        List<WorkProcessTemplateId> templateIds = templateAddable.getWorkProcessTemplateList().stream().map(v->v.id()).collect(Collectors.toList());
        CraftsmanShipTemplate craftsmanShip = new CraftsmanShipTemplate(randomTemplateIds(templateIds),templateRepository,
                materialId);
        return craftsmanShip;
    }

    public List<CraftsmanShipTemplate> createCraftsmanShipTemplates(){
        List<MaterialDTO> materialDTOS = client.materialsByMaterialType(MaterialType.PRODUCTION);
        List<CraftsmanShipTemplate> craftsmanShipTemplates = new ArrayList<>();
        for (MaterialDTO materialDTO: materialDTOS) {
            craftsmanShipTemplates.add(createCraftsmanShipTemplate(materialDTO.id()));
        }
        return craftsmanShipTemplates;
    }

    public static List<CraftsmanShipTemplateDTO> entityToDTO(List<CraftsmanShipTemplate> templateList){
        if (org.springframework.util.CollectionUtils.isEmpty(templateList)) {
            return new ArrayList<>();
        }
        return templateList.stream().map(v->CraftsmanShipTemplateDTO.create(v.id(),
                        v.getProductId(),v.getWorkProcessIds()))
                .collect(Collectors.toList());
    }

    public List<CraftsmanShipTemplate> getCraftsmanShipTemplates() {
        return craftsmanShipTemplates;
    }

    @Override
    @Transactional
    public void execute() {
        log.info("add all CraftsmanShipTemplate start");
        this.craftsmanShipTemplates = createCraftsmanShipTemplates();
        craftsmanShipRepository.saveAll(craftsmanShipTemplates);
        log.info("add all CraftsmanShipTemplate finished");
        manager.storeDataListToCache(entityToDTO(craftsmanShipTemplates));
    }
}
