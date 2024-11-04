package org.ddd.fundamental.workprocess.domain.model;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.core.AbstractAggregateRoot;
import org.ddd.fundamental.workprocess.domain.repository.WorkProcessNewRepository;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "w_craftsman_ship")
@Slf4j
public class CraftsmanShip extends AbstractAggregateRoot<CraftsmanShipId> {

    @Type(type = "json")
    @Column(columnDefinition = "json" , name = "work_process_ids")
    private List<WorkProcessId> workProcessIds = new ArrayList<>();

    @Transient
    private WorkProcessNewRepository repository;

    @SuppressWarnings("unused")
    private CraftsmanShip(){}

    public CraftsmanShip(List<WorkProcessId> workProcessIds,
                         WorkProcessNewRepository repository){
        super(CraftsmanShipId.randomId(CraftsmanShipId.class));
        removeDuplicate(workProcessIds);
        this.repository = repository;
        validate();
    }

    private void defaultWorkProcessIds() {
        if (null == workProcessIds) {
            this.workProcessIds = new ArrayList<>();
        }
    }

    private void removeDuplicate(List<WorkProcessId> workProcessIds){
        defaultWorkProcessIds();
        Set<WorkProcessId> workProcessIdSet = new HashSet<>();
        for (WorkProcessId workProcessId:workProcessIds) {
            if (!workProcessIdSet.contains(workProcessId)) {
                workProcessIdSet.add(workProcessId);
                this.workProcessIds.add(workProcessId);
            }
        }

    }

    private void validate(){
        List<WorkProcessNew> processList = repository.findByIdIn(new HashSet<>(workProcessIds));
        int size = processList.size();
        for (int i = 1 ; i < size-1; i++) {
            WorkProcessNew pre = processList.get(i-1);
            WorkProcessNew cur = processList.get(i);
            log.info(cur.toString());
            if (!pre.acceptNext(cur.id()) || !cur.acceptPre(pre.id())) {
                throw new RuntimeException("工序不正确");
            }
        }
    }

    public List<WorkProcessId> getWorkProcessIds() {
        return new ArrayList<>(workProcessIds);
    }

    public WorkProcessNewRepository getRepository() {
        return repository;
    }

    @Override
    public long created() {
        return 0;
    }
}
