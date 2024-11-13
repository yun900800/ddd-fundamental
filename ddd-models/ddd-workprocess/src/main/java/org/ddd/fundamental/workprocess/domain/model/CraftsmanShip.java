package org.ddd.fundamental.workprocess.domain.model;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.core.AbstractAggregateRoot;
import org.ddd.fundamental.workprocess.domain.repository.WorkProcessTemplateRepository;
import org.ddd.fundamental.workprocess.value.WorkProcessTemplateId;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.*;

@Entity
@Table(name = "w_craftsman_ship")
@Slf4j
public class CraftsmanShip extends AbstractAggregateRoot<CraftsmanShipId> {

    @Type(type = "json")
    @Column(columnDefinition = "json" , name = "work_process_ids")
    private List<WorkProcessTemplateId> workProcessIds = new ArrayList<>();

    @Transient
    private Map<WorkProcessTemplateId, WorkProcessTemplate> workProcessMap = new HashMap<>();

    @Transient
    private WorkProcessTemplateRepository repository;

    @SuppressWarnings("unused")
    private CraftsmanShip(){}

    public CraftsmanShip(List<WorkProcessTemplateId> workProcessIds,
                         WorkProcessTemplateRepository repository){
        super(CraftsmanShipId.randomId(CraftsmanShipId.class));
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
