package org.ddd.fundamental.workprocess.application.query;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.utils.CollectionUtils;
import org.ddd.fundamental.workorder.value.WorkOrderId;
import org.ddd.fundamental.workorder.value.WorkOrderValue;
import org.ddd.fundamental.workprocess.helper.WorkProcessHelper;
import org.ddd.fundamental.workprocess.domain.model.*;
import org.ddd.fundamental.workprocess.domain.repository.CraftsmanShipRepository;
import org.ddd.fundamental.workprocess.domain.repository.WorkProcessRecordRepository;
import org.ddd.fundamental.workprocess.domain.repository.WorkProcessTemplateRepository;
import org.ddd.fundamental.workprocess.value.CraftsmanShipId;
import org.ddd.fundamental.workprocess.value.WorkProcessTemplateId;
import org.ddd.fundamental.workprocess.value.WorkProcessValue;
import org.ddd.fundamental.workprocess.value.quantity.WorkProcessQuantity;
import org.ddd.fundamental.workprocess.value.resources.ProductResources;
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
    public void createWorkProcessRecords(MaterialId productId, CraftsmanShipId craftsmanShipId, WorkOrderId id,
                                         WorkOrderValue workOrderValue){
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
                    templateId,id,workOrderValue,template);
            recordList.add(record);
        }
        workProcessRecordRepository.saveAll(recordList);
        log.info("craftsmanShipAggragate is {}",craftsmanShipAggragate);
    }

    public WorkProcessRecord createProcessRecord(ChangeableInfo info,
                                                 ProductResources resources,
                                                 WorkProcessTemplateId templateId,
                                                 WorkOrderId id, WorkOrderValue workOrderValue,
                                                 WorkProcessTemplate template) {
        WorkProcessRecord record = WorkProcessRecord.create(
                info,
                WorkProcessValue.create(
                        resources,
                        templateId
                ),
                id,
                workOrderValue
        );
        WorkProcessTimeEntity workProcessTime = WorkProcessTimeEntity.init(
                CollectionUtils.random(WorkProcessHelper.trueOrFalse()));
        record.setWorkProcessTime(workProcessTime);
        WorkProcessQuantityEntity workProcessQuantity = WorkProcessQuantityEntity.create(
                template.getWorkProcessTemplateQuantity().isOverCross() ?
                        WorkProcessQuantity.buildOverCrossQuantity(template.getWorkProcessTemplateQuantity(),
                                workOrderValue.getProductQty().intValue()) :
                        WorkProcessQuantity.buildOwePaymentQuantity(template.getWorkProcessTemplateQuantity(),
                                workOrderValue.getProductQty().intValue())
        );
        record.setQuantity(workProcessQuantity);
        return record;
    }
}
