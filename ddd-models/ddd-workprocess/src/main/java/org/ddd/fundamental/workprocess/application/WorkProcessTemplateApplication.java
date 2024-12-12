package org.ddd.fundamental.workprocess.application;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.memoize.Memoize;
import org.ddd.fundamental.shared.api.optemplate.CraftsmanShipTemplateDTO;
import org.ddd.fundamental.shared.api.optemplate.WorkProcessTemplateDTO;
import org.ddd.fundamental.workprocess.creator.CraftsmanShipAddable;
import org.ddd.fundamental.workprocess.creator.WorkProcessTemplateAddable;
import org.ddd.fundamental.workprocess.domain.model.CraftsmanShipTemplate;
import org.ddd.fundamental.workprocess.domain.model.WorkProcessTemplate;
import org.ddd.fundamental.workprocess.domain.repository.CraftsmanShipRepository;
import org.ddd.fundamental.workprocess.domain.repository.WorkProcessTemplateRepository;
import org.ddd.fundamental.workprocess.value.WorkProcessTemplateId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(readOnly = true)
public class WorkProcessTemplateApplication {

    @Autowired
    private WorkProcessTemplateRepository templateRepository;

    @Autowired
    private CraftsmanShipRepository craftsmanShipRepository;

    @Autowired
    private WorkProcessTemplateAddable creator;

    @Autowired
    private CraftsmanShipAddable craftsmanShipAddable;


    public List<CraftsmanShipTemplateDTO> craftsmanShipTemplates(){
        List<CraftsmanShipTemplate> craftsmanShipTemplates =
                craftsmanShipRepository.findAll();
        return entityToDTOWithShipTemplate(craftsmanShipTemplates);
    }

    public static List<CraftsmanShipTemplateDTO> entityToDTOWithShipTemplate(List<CraftsmanShipTemplate> templates){
        return templates.stream().map(v->
                CraftsmanShipTemplateDTO.create(v.id(),v.getProductId(),v.getWorkProcessIds()))
                .collect(Collectors.toList());
    }


    @Memoize
    public List<WorkProcessTemplateDTO> workProcessTemplates() {
        List<WorkProcessTemplate> workProcessTemplates = templateRepository.findAll();
        return entityToDTO(workProcessTemplates);
    }

    // spring aop的局限性在于只能在一个类的一个方法中拦截
    // 而不能拦截方法的调用, 这是由于底层的代理机制决定的
    // 如果想拦截方法调用,有量中解决方案:
    // 1、将需要拦截的方法抽取到一个bean中
    // 2、或者使用AspectJ
    @Memoize
    public List<WorkProcessTemplate> queryByIds(List<String> ids){
        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }
        return templateRepository.findByIdIn(ids.stream()
                .map(v->new WorkProcessTemplateId(v)).collect(Collectors.toSet()));
    }

    public static List<WorkProcessTemplateDTO> entityToDTO(List<WorkProcessTemplate> templates){
        return templates.stream().map(v->
                        WorkProcessTemplateDTO.create(v.id(),v.getWorkProcessInfo(),v.getResources()))
                .collect(Collectors.toList());
    }

    public List<WorkProcessTemplateDTO> workProcessTemplatesByIds(List<String> ids){
        List<WorkProcessTemplate> workProcessTemplates = queryByIds(ids);
        return entityToDTO(workProcessTemplates);
    }
}
