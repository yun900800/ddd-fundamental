package org.ddd.fundamental.workprocess.domain.model;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.core.AbstractAggregateRoot;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.workprocess.domain.repository.WorkProcessTemplateRepository;
import org.ddd.fundamental.workprocess.value.CraftsmanShipId;
import org.ddd.fundamental.workprocess.value.WorkProcessTemplateId;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.*;

/**
 * 工艺模板
 */
@Entity
@Table(name = "w_craftsman_ship_template")
@Slf4j
public class CraftsmanShipTemplate extends AbstractAggregateRoot<CraftsmanShipId> {

    @Type(type = "json")
    @Column(columnDefinition = "json" , name = "work_process_ids")
    private List<WorkProcessTemplateId> workProcessIds = new ArrayList<>();

    /**
     * 工艺对应的产品id
     */
    private MaterialId productId;

    @Transient
    private Map<WorkProcessTemplateId, WorkProcessTemplate> workProcessMap = new HashMap<>();

    @Transient
    private WorkProcessTemplateRepository repository;

    @SuppressWarnings("unused")
    private CraftsmanShipTemplate(){}

    public CraftsmanShipTemplate(List<WorkProcessTemplateId> workProcessIds,
                                 WorkProcessTemplateRepository repository,
                                 MaterialId productId){
        super(CraftsmanShipId.randomId(CraftsmanShipId.class));
        this.productId = productId;
        removeDuplicate(workProcessIds);
        this.repository = repository;
        validate();
    }

    private void defaultWorkProcessIds() {
        if (null == workProcessIds) {
            this.workProcessIds = new ArrayList<>();
            this.workProcessMap = new HashMap<>();
        }
    }

    private void removeDuplicate(List<WorkProcessTemplateId> workProcessIds){
        defaultWorkProcessIds();
        Set<WorkProcessTemplateId> workProcessIdSet = new HashSet<>();
        for (WorkProcessTemplateId workProcessId:workProcessIds) {
            if (!workProcessIdSet.contains(workProcessId)) {
                workProcessIdSet.add(workProcessId);
                this.workProcessIds.add(workProcessId);
            }
        }
    }

    private void initCache(List<WorkProcessTemplate> processList) {
        for (WorkProcessTemplate processNew: processList) {
            workProcessMap.put(processNew.id(),processNew);
        }
    }

    /**
     * 从db中获取工序模板
     * @return
     */
    private List<WorkProcessTemplate> processNewsFromDB() {
        List<WorkProcessTemplate> processList = repository.findByIdIn(new HashSet<>(workProcessIds));
        return processList;
    }

    /**
     * 验证工序是否合法
     */
    private void validate(){
        List<WorkProcessTemplate> processList = processNewsFromDB();
        int size = processList.size();
        for (int i = 1 ; i < size-1; i++) {
            WorkProcessTemplate pre = processList.get(i-1);
            WorkProcessTemplate cur = processList.get(i);

            log.info(cur.toString());
            if (!pre.acceptNext(cur.id()) || !cur.acceptPre(pre.id())) {
                throw new RuntimeException("工序不正确");
            }
        }
        initCache(processList);
    }

    public List<WorkProcessTemplateId> getWorkProcessIds() {
        return new ArrayList<>(workProcessIds);
    }

    public WorkProcessTemplateRepository getRepository() {
        return repository;
    }

    public MaterialId getProductId() {
        return productId;
    }

    public Map<WorkProcessTemplateId, WorkProcessTemplate> getWorkProcessMap() {
        return workProcessMap;
    }

    @Override
    public long created() {
        return 0;
    }

    @Override
    public String toString() {
        return "CraftsmanShip{" +
                "workProcessIds=" + workProcessIds +
                ", workProcessMap=" + workProcessMap +
                '}';
    }
}
