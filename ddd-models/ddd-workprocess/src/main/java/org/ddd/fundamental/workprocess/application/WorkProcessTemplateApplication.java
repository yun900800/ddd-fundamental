package org.ddd.fundamental.workprocess.application;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.shared.api.optemplate.CraftsmanShipTemplateDTO;
import org.ddd.fundamental.workprocess.domain.model.CraftsmanShipTemplate;
import org.ddd.fundamental.workprocess.domain.repository.CraftsmanShipRepository;
import org.ddd.fundamental.workprocess.domain.repository.WorkProcessTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional( readOnly = true)
public class WorkProcessTemplateApplication {

    @Autowired
    private WorkProcessTemplateRepository templateRepository;

    @Autowired
    private CraftsmanShipRepository craftsmanShipRepository;

    @Autowired
    private WorkProcessCreator creator;


    public List<CraftsmanShipTemplateDTO> craftsmanShipTemplates(){
        List<CraftsmanShipTemplate> craftsmanShipTemplates = creator.getCraftsmanShipTemplates();
        if (CollectionUtils.isEmpty(craftsmanShipTemplates)) {
            craftsmanShipTemplates =
                    craftsmanShipRepository.findAll();
        }
        return craftsmanShipTemplates.stream().map(v->
                CraftsmanShipTemplateDTO.create(v.id(),v.getProductId(),v.getWorkProcessIds()))
                .collect(Collectors.toList());
    }
}
