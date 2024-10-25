package org.ddd.fundamental.workprocess.domain.model;

import org.ddd.fundamental.core.AbstractAggregateRoot;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 工序领域模型
 */
@Entity
@Table(name = "work_process")
public class WorkProcess extends AbstractAggregateRoot<WorkProcessId> {

    private String processName;

    @Type(type = "json")
    @Column(columnDefinition = "json" , name = "work_process_pre")
    private Set<WorkProcess> preProcesses = new HashSet<>();

    @Type(type = "json")
    @Column(columnDefinition = "json" , name = "work_process_next")
    private Set<WorkProcess> nextProcesses = new HashSet<>();

    /**
     * 工序目标数量
     */
    private int targetQuantity;

    /**
     * 工序不合格数量
     */
    private int unQualifiedQuantity;

    /**
     * 设置工序目标数量
     * @param targetQuantity
     * @return
     */
    public WorkProcess setTargetQuantity(int targetQuantity) {
        this.targetQuantity = targetQuantity;
        return this;
    }

    /**
     * 设置工序不合格数量
     * @param unQualifiedQuantity
     * @return
     */
    public WorkProcess setUnQualifiedQuantity(int unQualifiedQuantity) {
        this.unQualifiedQuantity = unQualifiedQuantity;
        return this;
    }

    public int targetQuantity(){
        return this.targetQuantity;
    }

    public int unQualifiedQuantity(){
        return this.unQualifiedQuantity;
    }

    @SuppressWarnings("unused")
    private WorkProcess(){
    }

    public WorkProcess(String processName){
        super(WorkProcessId.randomId(WorkProcessId.class));
        this.processName = processName;
    }

    /**
     * 添加前工序
     * @param processName
     * @return
     */
    public WorkProcess addPre(String processName){
        WorkProcess pre = new WorkProcess(processName);
        addPre(pre);
        return this;
    }

    public WorkProcess addPre(WorkProcess pre){
        this.preProcesses.add(pre);
        return this;
    }

    /**
     * 移出前工序
     * @param id
     * @return
     */
    public WorkProcess removePre(WorkProcessId id){
        WorkProcess process = new WorkProcess("");
        process.changeId(id);
        this.preProcesses.remove(process);
        return this;
    }

    /**
     * 添加后工序
     * @param processName
     * @return
     */
    public WorkProcess addNext(String processName){
        WorkProcess next = new WorkProcess(processName);
        addNext(next);
        return this;
    }

    public WorkProcess addNext(WorkProcess pre){
        this.nextProcesses.add(pre);
        return this;
    }

    /**
     * 移出后工序
     * @param id
     * @return
     */
    public WorkProcess removeNext(WorkProcessId id){
        WorkProcess process = new WorkProcess("");
        process.changeId(id);
        this.nextProcesses.remove(process);
        return this;
    }

    public boolean acceptNext(WorkProcess process){
        return this.nextProcesses.contains(process);
    }

    public boolean acceptPre(WorkProcess process){
        return this.preProcesses.contains(process);
    }

    public String getProcessName() {
        return processName;
    }

    public Set<WorkProcess> getPreProcesses() {
        return new HashSet<>(preProcesses);
    }

    public Set<WorkProcess> getNextProcesses() {
        return new HashSet<>(nextProcesses);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkProcess)) return false;
        WorkProcess that = (WorkProcess) o;
        return Objects.equals(id(), that.id());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id());
    }

    @Override
    public String toString() {
        return "WorkProcess{" +
                "processName='" + processName + '\'' +
//                ", preProcesses=" + preProcesses +
//                ", nextProcesses=" + nextProcesses +
                ", id = " + id() +
                '}';
    }
}
