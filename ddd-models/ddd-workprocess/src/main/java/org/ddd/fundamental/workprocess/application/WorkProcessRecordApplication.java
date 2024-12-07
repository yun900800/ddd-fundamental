package org.ddd.fundamental.workprocess.application;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.utils.CollectionUtils;
import org.ddd.fundamental.workprocess.creator.WorkProcessTemplateAddable;
import org.ddd.fundamental.workprocess.domain.model.CraftsmanShipTemplate;
import org.ddd.fundamental.workprocess.domain.model.WorkProcessRecord;
import org.ddd.fundamental.workprocess.domain.model.WorkProcessTemplate;
import org.ddd.fundamental.workprocess.domain.model.WorkProcessTimeEntity;
import org.ddd.fundamental.workprocess.domain.repository.CraftsmanShipRepository;
import org.ddd.fundamental.workprocess.domain.repository.WorkProcessRecordRepository;
import org.ddd.fundamental.workprocess.domain.repository.WorkProcessTemplateRepository;
import org.ddd.fundamental.workprocess.value.CraftsmanShipId;
import org.ddd.fundamental.workprocess.value.WorkProcessTemplateId;
import org.ddd.fundamental.workprocess.value.WorkProcessValue;
import org.ddd.fundamental.workprocess.value.resources.ProductResources;
import org.ddd.fundamental.workprocess.value.time.WorkProcessKeyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
public class WorkProcessRecordApplication {

    private final WorkProcessRecordRepository workProcessRecordRepository;


    private final CraftsmanShipRepository craftsmanShipRepository;

    private final WorkProcessTemplateRepository templateRepository;

    @Autowired
    public WorkProcessRecordApplication(
            WorkProcessRecordRepository workProcessRecordRepository,
            CraftsmanShipRepository craftsmanShipRepository,
            WorkProcessTemplateRepository templateRepository){
        this.workProcessRecordRepository = workProcessRecordRepository;
        this.craftsmanShipRepository = craftsmanShipRepository;
        this.templateRepository = templateRepository;
    }

    @Transactional
    public void createWorkProcessRecords(MaterialId productId, CraftsmanShipId craftsmanShipId){
        CraftsmanShipTemplate craftsmanShip = craftsmanShipRepository.findById(craftsmanShipId).orElse(null);
        if (craftsmanShip == null) {
            return;
        }
        CraftsmanShipTemplate craftsmanShipAggragate = new CraftsmanShipTemplate(craftsmanShip.getWorkProcessIds(),templateRepository,
                productId);
        List<WorkProcessRecord> recordList = new ArrayList<>();
        for (Map.Entry<WorkProcessTemplateId, WorkProcessTemplate> entry: craftsmanShipAggragate.getWorkProcessMap().entrySet()) {
            WorkProcessTemplate template = entry.getValue();
            WorkProcessTemplateId templateId = entry.getKey();
            WorkProcessRecord record = createProcessRecord(template.getWorkProcessInfo(),template.getResources(),
                    templateId);
            recordList.add(record);
        }
        workProcessRecordRepository.saveAll(recordList);
        log.info("craftsmanShipAggragate is {}",craftsmanShipAggragate);

    }

    public WorkProcessRecord createProcessRecord(ChangeableInfo info,
                                                 ProductResources resources,
                                                 WorkProcessTemplateId templateId) {
        WorkProcessRecord record = WorkProcessRecord.create(
                info,
                WorkProcessValue.create(
                        resources,
                        templateId
                )
        );
        WorkProcessTimeEntity workProcessTime = WorkProcessTimeEntity.init(
                CollectionUtils.random(WorkProcessTemplateAddable.trueOrFalse()));
        record.setWorkProcessTime(workProcessTime);
        return record;
    }
}
