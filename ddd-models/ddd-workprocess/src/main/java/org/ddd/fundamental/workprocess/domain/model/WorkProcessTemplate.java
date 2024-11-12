package org.ddd.fundamental.workprocess.domain.model;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.core.AbstractAggregateRoot;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.factory.ToolingEquipmentId;
import org.ddd.fundamental.workprocess.value.*;
import org.ddd.fundamental.workprocess.value.quantity.WorkProcessQuantity;
import org.ddd.fundamental.workprocess.value.resources.ProductResource;
import org.ddd.fundamental.workprocess.value.resources.ProductResources;
import org.ddd.fundamental.workprocess.value.time.AuxiliaryWorkTime;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "w_work_process_new")
public class WorkProcessTemplate extends AbstractAggregateRoot<WorkProcessId> {

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

    @Embedded
    private WorkProcessQuantity workProcessQuality;

    /**
     * 工序对应的设备ids
     */
    @Type(type = "json")
    @Column(columnDefinition = "json" , name = "equipment_ids")
    private Set<EquipmentId> equipmentIds = new HashSet<>();

    /**
     * 工序对应的工装制具ids
     */
    @Type(type = "json")
    @Column(columnDefinition = "json" , name = "tooling_ids")
    private Set<ToolingEquipmentId> toolingEquipmentIds = new HashSet<>();

    /**
     * 工序可以对接的前工序
     */
    @Type(type = "json")
    @Column(columnDefinition = "json" , name = "pre_ids")
    private Set<WorkProcessId> preWorkProcessIds = new HashSet<>();

    /**
     * 工序可以对接的后工序
     */
    @Type(type = "json")
    @Column(columnDefinition = "json" , name = "next_ids")
    private Set<WorkProcessId> nextWorkProcessIds = new HashSet<>();

    @Embedded
    private WorkProcessBeat workProcessBeat;

    @Embedded
    private ProductResources resources = new ProductResources(new ArrayList<>());


    @SuppressWarnings("unused")
    private WorkProcessTemplate(){}

    public WorkProcessTemplate(ChangeableInfo workProcessInfo,
                               AuxiliaryWorkTime auxiliaryWorkTime,
                               WorkProcessQuantity workProcessQuality,
                               WorkProcessBeat workProcessBeat){
        super(WorkProcessId.randomId(WorkProcessId.class));
        this.workProcessInfo = workProcessInfo;
        this.auxiliaryWorkTime = auxiliaryWorkTime;
        this.workProcessQuality = workProcessQuality;
        this.workProcessBeat = workProcessBeat;
        this.equipmentIds = new HashSet<>();
        this.toolingEquipmentIds = new HashSet<>();
    }

    public WorkProcessTemplate addResource(ProductResource resource){
        this.resources.addResource(resource);
        return this;
    }

    public WorkProcessTemplate removeResource(ProductResource resource){
        this.resources.removeResource(resource);
        return this;
    }

    private void defaultEquipmentIds(){
        if (null == equipmentIds) {
            this.equipmentIds = new HashSet<>();
        }
    }

    public WorkProcessTemplate addEquipmentId(EquipmentId id){
        defaultEquipmentIds();
        this.equipmentIds.add(id);
        return this;
    }

    public WorkProcessTemplate removeEquipmentId(EquipmentId id){
        defaultEquipmentIds();
        this.equipmentIds.remove(id);
        return this;
    }

    public WorkProcessTemplate clearEquipmentIds(){
        defaultEquipmentIds();
        this.equipmentIds.clear();
        return this;
    }

    private void defaultToolingIds(){
        if (null == toolingEquipmentIds){
            this.toolingEquipmentIds = new HashSet<>();
        }
    }

    public WorkProcessTemplate addToolingId(ToolingEquipmentId id){
        defaultToolingIds();
        this.toolingEquipmentIds.add(id);
        return this;
    }

    public WorkProcessTemplate removeToolingId(ToolingEquipmentId id){
        defaultToolingIds();
        this.toolingEquipmentIds.remove(id);
        return this;
    }
    public WorkProcessTemplate clearToolingIds(){
        defaultToolingIds();
        this.toolingEquipmentIds.clear();
        return this;
    }

    private void defaultPreIds(){
        if (this.preWorkProcessIds == null) {
            this.preWorkProcessIds = new HashSet<>();
        }
    }

    public WorkProcessTemplate addPreId(WorkProcessId preId){
        defaultPreIds();
        this.preWorkProcessIds.add(preId);
        return this;
    }

    public WorkProcessTemplate removePreId(WorkProcessId preId){
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

    public WorkProcessTemplate addNextId(WorkProcessId preId){
        defaultNextIds();
        this.nextWorkProcessIds.add(preId);
        return this;
    }

    public WorkProcessTemplate removeNextId(WorkProcessId preId){
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
    public boolean acceptPre(WorkProcessId id){
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
    public boolean acceptNext(WorkProcessId id){
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

    public WorkProcessQuantity getWorkProcessQuality() {
        return workProcessQuality.clone();
    }

    public Set<EquipmentId> getEquipmentIds() {
        return new HashSet<>(equipmentIds);
    }

    public Set<ToolingEquipmentId> getToolingEquipmentIds() {
        return new HashSet<>(toolingEquipmentIds);
    }

    public Set<WorkProcessId> getPreWorkProcessIds() {
        return new HashSet<>(preWorkProcessIds);
    }

    public Set<WorkProcessId> getNextWorkProcessIds() {
        return new HashSet<>(nextWorkProcessIds);
    }

    public WorkProcessBeat getWorkProcessBeat() {
        return workProcessBeat.clone();
    }

    @Override
    public String toString() {
        return "WorkProcessNew{" +
                "workProcessInfo=" + workProcessInfo +
                ", auxiliaryWorkTime=" + auxiliaryWorkTime +
                ", workProcessQuality=" + workProcessQuality +
                ", equipmentIds=" + equipmentIds +
                ", toolingEquipmentIds=" + toolingEquipmentIds +
                ", preWorkProcessIds=" + preWorkProcessIds +
                ", nextWorkProcessIds=" + nextWorkProcessIds +
                ", workProcessBeat=" + workProcessBeat +
                '}';
    }

    @Override
    public long created() {
        return 0;
    }
}
