package org.ddd.fundamental.workprocess.domain.model;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.core.AbstractAggregateRoot;
import org.ddd.fundamental.workprocess.value.*;
import org.ddd.fundamental.workprocess.value.controller.WorkProcessTemplateControl;
import org.ddd.fundamental.workprocess.value.quantity.WorkProcessTemplateQuantity;
import org.ddd.fundamental.workprocess.value.resources.ProductResource;
import org.ddd.fundamental.workprocess.value.resources.ProductResources;
import org.ddd.fundamental.workprocess.value.time.AuxiliaryWorkTime;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 工序模板
 */
@Entity
@Table(name = "w_work_process_template")
public class WorkProcessTemplate extends AbstractAggregateRoot<WorkProcessTemplateId> {

    /**
     * 工序基本信息
     */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "work_process_name", nullable = false)),
            @AttributeOverride(name = "desc", column = @Column(name = "work_process_desc" ,nullable = false)),
            @AttributeOverride(name = "isUse", column = @Column(name = "work_process_is_use" ,nullable = false))
    })
    private ChangeableInfo workProcessInfo;

    /**
     * 工序辅助作业时间
     */
    @Embedded
    private AuxiliaryWorkTime auxiliaryWorkTime;

    /**
     * 工序可以对接的前工序
     */
    @Type(type = "json")
    @Column(columnDefinition = "json" , name = "pre_ids")
    private Set<WorkProcessTemplateId> preWorkProcessIds = new HashSet<>();

    /**
     * 工序可以对接的后工序
     */
    @Type(type = "json")
    @Column(columnDefinition = "json" , name = "next_ids")
    private Set<WorkProcessTemplateId> nextWorkProcessIds = new HashSet<>();

    @Embedded
    private WorkProcessBeat workProcessBeat;

    @Embedded
    private ProductResources resources = new ProductResources(new HashSet<>());

    @Embedded
    private WorkProcessTemplateControl workProcessController;

    @Embedded
    private WorkProcessTemplateQuantity workProcessTemplateQuantity;


    @SuppressWarnings("unused")
    private WorkProcessTemplate(){}

    public WorkProcessTemplate(ChangeableInfo workProcessInfo,
                               AuxiliaryWorkTime auxiliaryWorkTime,
                               WorkProcessBeat workProcessBeat,
                               WorkProcessTemplateControl workProcessController,
                               WorkProcessTemplateQuantity workProcessTemplateQuantity){
        super(WorkProcessTemplateId.randomId(WorkProcessTemplateId.class));
        this.workProcessInfo = workProcessInfo;
        this.auxiliaryWorkTime = auxiliaryWorkTime;
        this.workProcessBeat = workProcessBeat;
        this.workProcessController = workProcessController;
        this.workProcessTemplateQuantity = workProcessTemplateQuantity;
    }

    public WorkProcessTemplate addResource(ProductResource resource){
        this.resources.addResource(resource);
        return this;
    }

    public WorkProcessTemplate removeResource(ProductResource resource){
        this.resources.removeResource(resource);
        return this;
    }

    private void defaultPreIds(){
        if (this.preWorkProcessIds == null) {
            this.preWorkProcessIds = new HashSet<>();
        }
    }

    public WorkProcessTemplate addPreId(WorkProcessTemplateId preId){
        defaultPreIds();
        this.preWorkProcessIds.add(preId);
        return this;
    }

    public WorkProcessTemplate removePreId(WorkProcessTemplateId preId){
        defaultPreIds();
        this.preWorkProcessIds.remove(preId);
        return this;
    }

    public WorkProcessTemplate clearPreIds(){
        defaultPreIds();
        this.preWorkProcessIds.clear();
        return this;
    }

    private void defaultNextIds(){
        if (this.nextWorkProcessIds == null) {
            this.nextWorkProcessIds = new HashSet<>();
        }
    }

    public WorkProcessTemplate addNextId(WorkProcessTemplateId preId){
        defaultNextIds();
        this.nextWorkProcessIds.add(preId);
        return this;
    }

    public WorkProcessTemplate removeNextId(WorkProcessTemplateId preId){
        defaultNextIds();
        this.nextWorkProcessIds.remove(preId);
        return this;
    }

    public WorkProcessTemplate clearNextIds(){
        defaultNextIds();
        this.nextWorkProcessIds.clear();
        return this;
    }

    /**
     * 判断某个工序是否可以作为当前工序的前工序
     * 如果前工序集合为空,则接收所有
     * 否则需要判断某个工序是否在当前的前工序集合中
     * @param id
     * @return
     */
    public boolean acceptPre(WorkProcessTemplateId id){
        if (null == preWorkProcessIds || preWorkProcessIds.isEmpty()){
            return true;
        }
        return preWorkProcessIds.contains(id);
    }

    /**
     * 判断某个工序是否可以作为当前工序的后工序
     * 如果后工序集合为空,则接受所有工序
     * 否则需要判断某个工序是否在当前的后工序集合中
     * @param id
     * @return
     */
    public boolean acceptNext(WorkProcessTemplateId id){
        if (null == nextWorkProcessIds || nextWorkProcessIds.isEmpty()){
            return true;
        }
        return nextWorkProcessIds.contains(id);
    }

    public ChangeableInfo getWorkProcessInfo() {
        return workProcessInfo.clone();
    }

    public AuxiliaryWorkTime getAuxiliaryWorkTime() {
        return auxiliaryWorkTime.clone();
    }

    public Set<WorkProcessTemplateId> getPreWorkProcessIds() {
        return new HashSet<>(preWorkProcessIds);
    }

    public Set<WorkProcessTemplateId> getNextWorkProcessIds() {
        return new HashSet<>(nextWorkProcessIds);
    }

    public WorkProcessBeat getWorkProcessBeat() {
        return workProcessBeat.clone();
    }

    public ProductResources getResources() {
        return resources;
    }

    public WorkProcessTemplateControl getWorkProcessController() {
        return workProcessController.clone();
    }

    public WorkProcessTemplateQuantity getWorkProcessTemplateQuantity() {
        return workProcessTemplateQuantity.clone();
    }

    @Override
    public String toString() {
        return "WorkProcessNew{" +
                "workProcessInfo=" + workProcessInfo +
                ", auxiliaryWorkTime=" + auxiliaryWorkTime +
                ", preWorkProcessIds=" + preWorkProcessIds +
                ", nextWorkProcessIds=" + nextWorkProcessIds +
                ", resources=" + resources +
                ", workProcessBeat=" + workProcessBeat +
                '}';
    }

    @Override
    public long created() {
        return 0;
    }
}
