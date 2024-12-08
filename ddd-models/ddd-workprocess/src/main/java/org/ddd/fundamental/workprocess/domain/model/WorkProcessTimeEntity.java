package org.ddd.fundamental.workprocess.domain.model;

import org.ddd.fundamental.core.AbstractEntity;
import org.ddd.fundamental.infra.hibernate.comment.Comment;
import org.ddd.fundamental.workprocess.enums.WorkProcessTimeState;
import org.ddd.fundamental.workprocess.value.WorkProcessTimeId;
import org.ddd.fundamental.workprocess.value.time.WorkProcessKeyTime;
import org.ddd.fundamental.workprocess.value.time.WorkProcessTimeEvent;

import javax.persistence.*;

@Entity
@Table(name = "w_work_process_time")
@Comment("这是一个管理时间的表")
public class WorkProcessTimeEntity extends AbstractEntity<WorkProcessTimeId> {

    @Embedded
    private WorkProcessKeyTime keyTime;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private WorkProcessRecord record;

    /**
     * 是否需要换线
     */
    private boolean isNeedChangeLine;

    @SuppressWarnings("unused")
    protected WorkProcessTimeEntity(){}

    private WorkProcessTimeEntity(WorkProcessKeyTime keyTime){
        super(WorkProcessTimeId.randomId(WorkProcessTimeId.class));
        this.keyTime = keyTime;
        this.isNeedChangeLine = false;
    }

    public static WorkProcessTimeEntity create(WorkProcessKeyTime keyTime){
        return new WorkProcessTimeEntity(keyTime);
    }

    public static WorkProcessTimeEntity init(boolean isNeedChangeLine) {
        WorkProcessTimeEntity workProcessTime = new WorkProcessTimeEntity(WorkProcessKeyTime.init());
        workProcessTime.needChangeLine(isNeedChangeLine);
        return workProcessTime;
    }

    /**
     * 直接启动工序
     * @return
     */
    public WorkProcessTimeEntity directStartProcess(){
        if (WorkProcessTimeState.INIT.equals(this.keyTime.getState())) {
            WorkProcessKeyTime.getStateMachine()
                    .fireEvent(this.keyTime.getState(),
                            WorkProcessTimeEvent.WORK_PROCESS_START_EVENT,this.keyTime);
        }
        return this;
    }

    public WorkProcessRecord getRecord() {
        return record;
    }

    public void setRecord(WorkProcessRecord record) {
        this.record = record;
    }

    /**
     * 直接启动换线
     * @return
     */
    public WorkProcessTimeEntity directChangLine(){
        if (WorkProcessTimeState.INIT.equals(this.keyTime.getState())) {
            WorkProcessKeyTime.getStateMachine()
                    .fireEvent(this.keyTime.getState(),
                            WorkProcessTimeEvent.CHANGE_LINE_START_EVENT,this.keyTime);
        }
        return this;
    }

    /**
     * 修改是否需要换线
     * @param isNeedChangeLine
     * @return
     */
    public WorkProcessTimeEntity needChangeLine(boolean isNeedChangeLine){
        if(this.isNeedChangeLine){
            throw new RuntimeException("换线工序不能切换成不换线的工序");
        }
        this.isNeedChangeLine = isNeedChangeLine;
        return this;
    }

    /**
     * 工序是否需要换线
     * @return
     */
    public boolean isNeedChangeLine() {
        return isNeedChangeLine;
    }

    /**
     * 换线后启动工序
     * @return
     */
    public WorkProcessTimeEntity startProcessAfterChangingLine(){
        if (!isNeedChangeLine()){
            throw new RuntimeException("请先启动换线后再启动工序");
        }
        if (!WorkProcessTimeState.LINE_CHANGED.equals(this.keyTime.getState())) {
            throw new RuntimeException("工序处于换线中才能启动");
        }
        WorkProcessKeyTime.getStateMachine()
                .fireEvent(WorkProcessTimeState.LINE_CHANGED,
                WorkProcessTimeEvent.WORK_PROCESS_START_EVENT,this.keyTime);
        return this;
    }

    /**
     * 中断工序
     * @return
     */
    public WorkProcessTimeEntity interruptProcess(){
        if (WorkProcessTimeState.WORK_PROCESS_RUNNING.equals(this.keyTime.getState())){
            WorkProcessKeyTime.getStateMachine()
                    .fireEvent(WorkProcessTimeState.WORK_PROCESS_RUNNING,
                    WorkProcessTimeEvent.WORK_PROCESS_INTERRUPT_EVENT,this.keyTime);
        } else {
            throw new RuntimeException("工序处于运行中的状态才可以中断");
        }
        return this;
    }

    /**
     * 中断后开始检查工序
     * @return
     */
    public WorkProcessTimeEntity checkAfterInterrupt(){
        if (WorkProcessTimeState.WORK_PROCESS_INTERRUPTED.equals(this.keyTime.getState())) {
            WorkProcessKeyTime.getStateMachine()
                    .fireEvent(WorkProcessTimeState.WORK_PROCESS_INTERRUPTED,
                            WorkProcessTimeEvent.WORK_PROCESS_CHECK_EVENT,this.keyTime);
        } else{
            throw new RuntimeException("工序处于中断状态才能检查");
        }
        return this;
    }

    /**
     * 重启工序
     * @return
     */
    public WorkProcessTimeEntity restartProcess(){
        if (WorkProcessTimeState.WORK_PROCESS_CHECKED.equals(this.keyTime.getState())
        || WorkProcessTimeState.WORK_PROCESS_INTERRUPTED.equals(this.keyTime.getState())) {
            WorkProcessKeyTime.getStateMachine()
                    .fireEvent(this.keyTime.getState(),
                            WorkProcessTimeEvent.WORK_PROCESS_RESTART_EVENT,this.keyTime);
        } else {
            throw new RuntimeException("只有处于检查或者中断状态才能重启");
        }
        return this;
    }

    public WorkProcessTimeEntity finishProcess(){
        if (WorkProcessTimeState.WORK_PROCESS_CHECKED.equals(this.keyTime.getState())
                || WorkProcessTimeState.WORK_PROCESS_INTERRUPTED.equals(this.keyTime.getState())
                || WorkProcessTimeState.WORK_PROCESS_RUNNING.equals(this.keyTime.getState())) {
            WorkProcessKeyTime.getStateMachine()
                    .fireEvent(this.keyTime.getState(),
                            WorkProcessTimeEvent.WORK_PROCESS_FINISH_EVENT,this.keyTime);
        } else {
            throw new RuntimeException("只有处于检查或者中断或者运行状态才能结束工序");
        }
        return this;
    }

    public WorkProcessTimeEntity offlineProcess(){
        if (WorkProcessTimeState.WORK_PROCESS_FINISHED.equals(this.keyTime.getState())){
            WorkProcessKeyTime.getStateMachine()
                    .fireEvent(this.keyTime.getState(),
                            WorkProcessTimeEvent.WORK_PROCESS_OFFLINE_EVENT,this.keyTime);
        } else {
            throw new RuntimeException("只有处于结束状态才能下线工序");
        }
        return this;
    }

    public WorkProcessTimeEntity transfer(){
        if (WorkProcessTimeState.WORK_PROCESS_OFFLINE.equals(this.keyTime.getState())){
            WorkProcessKeyTime.getStateMachine()
                    .fireEvent(this.keyTime.getState(),
                            WorkProcessTimeEvent.WORK_PROCESS_TRANSFER_EVENT,this.keyTime);
        } else {
            throw new RuntimeException("只有处于下线状态才能开始运输");
        }
        return this;
    }

    public WorkProcessTimeEntity finishTransfer(){
        if (WorkProcessTimeState.WORK_PROCESS_TRANSFER.equals(this.keyTime.getState())){
            WorkProcessKeyTime.getStateMachine()
                    .fireEvent(this.keyTime.getState(),
                            WorkProcessTimeEvent.WORK_PROCESS_TRANSFER_OVER_EVENT,this.keyTime);
        } else {
            throw new RuntimeException("只有处于下线状态才能开始运输");
        }
        return this;
    }


    public WorkProcessKeyTime getKeyTime() {
        return keyTime;
    }

    @Override
    public String toString() {
        return "WorkProcessTimeEntity{" +
                "keyTime=" + keyTime +
                '}';
    }

    @Override
    public long created() {
        return 0;
    }
}
