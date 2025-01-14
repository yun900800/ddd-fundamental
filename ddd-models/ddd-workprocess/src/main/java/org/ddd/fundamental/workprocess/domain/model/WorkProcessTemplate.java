package org.ddd.fundamental.workprocess.domain.model;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.core.AbstractAggregateRoot;
import org.ddd.fundamental.workprocess.value.*;
import org.ddd.fundamental.workprocess.value.quantity.WorkProcessTemplateQuantity;
import org.ddd.fundamental.workprocess.value.resources.ProductResource;
import org.ddd.fundamental.workprocess.value.resources.ProductResources;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;



/**
 * 工序模板
 * 注意,紧密的一对一关系使用双向关联(创建的时候一定要都存在,否则会报错误)
 * 而松散的一对一关系使用单向关联,因为这能避免n+1查询问题
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
    private WorkProcessTemplateQuantity workProcessTemplateQuantity;

    @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "template_id")
    private WorkProcessTemplateControl control;

    public WorkProcessTemplateControl getControl() {
        return control;
    }

    @SuppressWarnings("unused")
    private WorkProcessTemplate(){}

    public WorkProcessTemplate(ChangeableInfo workProcessInfo,
                               WorkProcessBeat workProcessBeat,
                               WorkProcessTemplateQuantity workProcessTemplateQuantity){
        super(WorkProcessTemplateId.randomId(WorkProcessTemplateId.class));
        this.workProcessInfo = workProcessInfo;
        this.workProcessBeat = workProcessBeat;
        this.workProcessTemplateQuantity = workProcessTemplateQuantity;
    }


    public WorkProcessTemplate setControl(WorkProcessTemplateControl control){
        this.control = control;
        changeUpdated();
        return this;
    }

    /**
     * 修改工序模版的name,desc,isUse信息
     * @param info
     * @return
     */
    public WorkProcessTemplate changeInfo(ChangeableInfo info){
        this.workProcessInfo = info;
        return this;
    }

    public WorkProcessTemplate changeName(String name){
        this.workProcessInfo = workProcessInfo.changeName(name);
        return this;
    }

    public WorkProcessTemplate changeDesc(String desc){
        this.workProcessInfo = workProcessInfo.changeDesc(desc);
        return this;
    }

    public WorkProcessTemplate enableUse(){
        this.workProcessInfo = workProcessInfo.enableUse();
        return this;
    }

    /**
     * 注意,修改数量和节拍都是整体修改,粒度是比较粗的哈
     * 可以增加更细粒度的API来进行修改
     * 因此模型的精化是一步一步的迭代过程
     * @param beat
     * @return
     */
    public WorkProcessTemplate changeWorkProcessBeat(WorkProcessBeat beat){
        this.workProcessBeat = beat;
        changeUpdated();
        return this;
    }


    public WorkProcessTemplate changeQuantity(WorkProcessTemplateQuantity quantity) {
        this.workProcessTemplateQuantity = quantity;
        changeUpdated();
        return this;
    }

    public WorkProcessTemplate addResource(ProductResource resource){
        this.resources = this.resources.addResource(resource);
        changeUpdated();
        return this;
    }

    public WorkProcessTemplate removeResource(ProductResource resource){
        this.resources = this.resources.removeResource(resource);
        changeUpdated();
        return this;
    }

    private void defaultPreIds(){
        if (this.preWorkProcessIds == null) {
            this.preWorkProcessIds = new HashSet<>();
        }
    }

    private void idIsNotValid(WorkProcessTemplateId id){
        if (id.equals(this.id())) {
            String msg = "不能添加与当前模板一致的id:{}";
            throw new RuntimeException(String.format(msg,id.toUUID()));
        }
    }

    public WorkProcessTemplate configurePreId(WorkProcessTemplateId preId){
        defaultPreIds();
        idIsNotValid(preId);
        this.preWorkProcessIds.add(preId);
        changeUpdated();
        return this;
    }

    public WorkProcessTemplate removePreId(WorkProcessTemplateId preId){
        defaultPreIds();
        this.preWorkProcessIds.remove(preId);
        changeUpdated();
        return this;
    }

    public WorkProcessTemplate clearPreIds(){
        defaultPreIds();
        this.preWorkProcessIds.clear();
        changeUpdated();
        return this;
    }

    private void defaultNextIds(){
        if (this.nextWorkProcessIds == null) {
            this.nextWorkProcessIds = new HashSet<>();
        }
    }

    public WorkProcessTemplate configureNextId(WorkProcessTemplateId nextId){
        defaultNextIds();
        idIsNotValid(nextId);
        this.nextWorkProcessIds.add(nextId);
        changeUpdated();
        return this;
    }

    public WorkProcessTemplate removeNextId(WorkProcessTemplateId nextId){
        defaultNextIds();
        this.nextWorkProcessIds.remove(nextId);
        changeUpdated();
        return this;
    }

    public WorkProcessTemplate clearNextIds(){
        defaultNextIds();
        this.nextWorkProcessIds.clear();
        changeUpdated();
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

    public WorkProcessTemplateQuantity getWorkProcessTemplateQuantity() {
        return workProcessTemplateQuantity.clone();
    }

    @Override
    public String toString() {
        return "WorkProcessNew{" +
                "workProcessInfo=" + workProcessInfo +
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
