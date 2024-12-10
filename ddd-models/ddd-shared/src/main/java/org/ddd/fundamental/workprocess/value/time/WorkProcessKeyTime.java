package org.ddd.fundamental.workprocess.value.time;

import com.alibaba.cola.statemachine.StateMachine;
import org.ddd.fundamental.core.ValueObject;
import org.ddd.fundamental.core.machine.Context;
import org.ddd.fundamental.infra.hibernate.comment.Comment;
import org.ddd.fundamental.workprocess.enums.WorkProcessTimeState;

import javax.persistence.*;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

/**
 * 工序关键时间(跟工序数据有关而跟工序模板无关)
 * 这部分与工序时间相关的操作可以抽象成一个状态机
 */
@MappedSuperclass
@Embeddable
public class WorkProcessKeyTime extends Context implements ValueObject, Cloneable  {

    @Comment("初始化时间")
    @Column
    private Instant initTime;

    /**
     * 工序开始执行时间
     */
    @Comment("工序开始执行时间")
    @Column
    private Instant startTime;

    /**
     * 工序结束执行时间
     */
    @Comment("工序结束执行时间")
    private Instant endTime;

    /**
     * 工序中断执行时间
     */
    private Instant interruptTime;

    /**
     * 工序重启时间
     */
    private Instant restartTime;

    /**
     * 换线是否的设置时间
     */
    private Instant changeLineSetTime;

    /**
     * 下线时间
     */
    private Instant offlineTime;

    /**
     * 下线后设备运输时间
     */
    private Instant transferTime;

    /**
     * 检查开始时间
     */
    private Instant startCheckTime;

    private String reason;

    private Instant transferFinishTime;

    @Enumerated(EnumType.STRING)
    private WorkProcessTimeState state;

    @Transient
    private static StateMachine<WorkProcessTimeState, WorkProcessTimeEvent, Context>
        stateMachine = new WorkProcessTimeStateMachine().createMachine();;

    @SuppressWarnings("unused")
    private WorkProcessKeyTime(){}

    private WorkProcessKeyTime(Instant startTime,
                               Instant endTime,
                               Instant interruptTime,
                               String reason,
                               Instant restartTime,
                               Instant changeLineSetTime,
                               Instant offlineTime,
                               Instant startCheckTime,
                               Instant transferTime,
                               WorkProcessTimeState state
                               ){
        this.startTime = startTime;
        this.endTime = endTime;
        this.interruptTime = interruptTime;
        this.restartTime = restartTime;
        this.reason = reason;
        this.state = state;
        this.changeLineSetTime = changeLineSetTime;
        this.offlineTime = offlineTime;
        this.startCheckTime = startCheckTime;
        this.transferTime = transferTime;
    }

    private WorkProcessKeyTime(Instant startTime, Instant changeLineSetTime){
        this.changeLineSetTime = changeLineSetTime;
        this.startTime = startTime;
        this.state = WorkProcessTimeState.INIT;
        this.initTime = Instant.now();
    }
    public static WorkProcessKeyTime init(){
        return new WorkProcessKeyTime(null,null);
    }

    public static StateMachine<WorkProcessTimeState, WorkProcessTimeEvent, Context> getStateMachine() {
        return stateMachine;
    }

    public WorkProcessKeyTime directStartProcess(Instant startTime){
        this.startTime = startTime;
        return this;
    }

    public WorkProcessKeyTime directStartProcess(){
        this.directStartProcess(Instant.now());
        return this;
    }

    public WorkProcessKeyTime directChangingLine(Instant changeLineSetTime){
        this.changeLineSetTime = changeLineSetTime;
        return this;
    }

    public WorkProcessKeyTime directChangingLine(){
        this.directChangingLine(Instant.now());
        return this;
    }

    public WorkProcessKeyTime changeState(WorkProcessTimeState state){
        this.state = state;
        return this;
    }



    /**
     * 开始检查工序
     * @param startCheckTime
     * @return
     */
    public WorkProcessKeyTime startCheck(Instant startCheckTime){
        this.startCheckTime = startCheckTime;
        return this;
    }

    public WorkProcessKeyTime startCheck(){
        startCheck(Instant.now());
        return this;
    }

    /**
     * 工序启动
     * @param startTime
     * @return
     */
    public WorkProcessKeyTime startProcess(Instant startTime){
        if (this.changeLineSetTime == null) {
            throw new RuntimeException("启动工序的时候先要设置换线时间");
        }
        this.startTime = startTime;
        return this;
    }

    /**
     * 工序启动
     * @param
     * @return
     */
    public WorkProcessKeyTime startProcess(){
        startProcess(Instant.now());
        return this;
    }

    /**
     * 换线开始设置时间
     * @param setTime
     * @return
     */
    public WorkProcessKeyTime startSetTime(Instant setTime){
        if (null != this.startTime){
            throw new RuntimeException("工序已经开始启动啦,不能再设置换线时间");
        }
        this.changeLineSetTime = setTime;

        return this;
    }

    /**
     * 换线开始设置时间
     * @return
     */
    public WorkProcessKeyTime startSetTime(){
        this.startSetTime(Instant.now());
        return this;
    }

    /**
     * 开始下线
     * @param offlineTime
     * @return
     */
    public WorkProcessKeyTime startOffline(Instant offlineTime) {
        if (this.endTime == null) {
            throw new RuntimeException("工序没有结束的时候不能下线");
        }
        this.offlineTime = offlineTime;
        return this;
    }

    /**
     * 开始下线
     * @param
     * @return
     */
    public WorkProcessKeyTime startOffline() {
        this.startOffline(Instant.now());
        return this;
    }

    /**
     * 开始运输
     * @param transferTime
     * @return
     */
    public WorkProcessKeyTime startTransfer(Instant transferTime){
        if (this.offlineTime == null) {
            throw new RuntimeException("工序没有下线的时候不能运输设备或者生成运输工单");
        }
        this.transferTime = transferTime;
        return this;
    }

    /**
     * 开始运输
     * @return
     */
    public WorkProcessKeyTime startTransfer(){
        this.startTransfer(Instant.now());
        return this;
    }

    public WorkProcessKeyTime finishTransfer(Instant transferFinishTime){
        this.transferFinishTime = transferFinishTime;
        return this;
    }

    public WorkProcessKeyTime finishTransfer(){
        this.finish(Instant.now());
        return this;
    }


    public WorkProcessKeyTime finish(Instant endTime){
        if (endTime.isBefore(this.startTime)) {
            throw new RuntimeException("工序结束时间不能早于工序开始时间");
        }
        if (null != this.interruptTime && endTime.isBefore(this.interruptTime)) {
            throw new RuntimeException("工序结束时间不能早于工序运行中的中断时间");
        }
        if (null != this.restartTime && endTime.isBefore(this.restartTime)) {
            throw new RuntimeException("工序结束时间不能早于工序运行中的重启时间");
        }
        this.endTime = endTime;
        return new WorkProcessKeyTime(startTime,endTime,interruptTime,reason,restartTime,changeLineSetTime,
                offlineTime,startCheckTime,transferTime,state);
    }

    public WorkProcessKeyTime finish() {
        Instant endTime = Instant.now();
        return finish(endTime);
    }

    public WorkProcessKeyTime interrupt() {
        Instant interruptTime = Instant.now();
        return interrupt(interruptTime);
    }

    public WorkProcessKeyTime interrupt(Instant interruptTime) {
        if (interruptTime.isBefore(this.startTime)) {
            throw new RuntimeException("工序中断时间不能早于工序开始时间");
        }
        this.interruptTime = interruptTime;
        return new WorkProcessKeyTime(this.startTime,this.endTime,interruptTime,reason,restartTime
                ,changeLineSetTime,
                offlineTime,startCheckTime,transferTime,state);
    }

    public WorkProcessKeyTime changeReason(String reason) {
        this.reason = reason;
        return new WorkProcessKeyTime(this.startTime,this.endTime,interruptTime,reason,restartTime
                ,changeLineSetTime,
                offlineTime,startCheckTime,transferTime,state);
    }

    public WorkProcessKeyTime restart() {
        Instant restartTime = Instant.now();
        return restart(restartTime);
    }

    public WorkProcessKeyTime restart(Instant restartTime) {
        if (restartTime.isBefore(this.startTime)) {
            throw new RuntimeException("工序重启时间不能早于工序开始时间");
        }
        if (null == this.interruptTime) {
            throw new RuntimeException("工序没有中断过,不需要重启");
        }
        if (null != this.interruptTime && restartTime.isBefore(this.interruptTime)) {
            throw new RuntimeException("工序重启时间不能早于工序中断时间");
        }
        this.restartTime = restartTime;
        return new WorkProcessKeyTime(this.startTime,this.endTime,interruptTime,reason,restartTime,changeLineSetTime,
                offlineTime,startCheckTime,transferTime,state);
    }

    public long interruptRange() {
        return Duration.between(interruptTime,restartTime).toMinutes();
    }

    public Instant getStartTime() {
        return startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public Instant getInterruptTime() {
        return interruptTime;
    }

    public String getReason() {
        return reason;
    }

    public Instant getRestartTime() {
        return restartTime;
    }

    public Instant getChangeLineSetTime() {
        return changeLineSetTime;
    }

    public Instant getOfflineTime() {
        return offlineTime;
    }

    public Instant getTransferTime() {
        return transferTime;
    }

    public Instant getTransferFinishTime() {
        return transferFinishTime;
    }

    @Override
    public String toString() {
        return "WorkProcessKeyTime{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", interruptTime=" + interruptTime +
                ", restartTime=" + restartTime +
                ", changeLineSetTime=" + changeLineSetTime +
                ", offlineTime=" + offlineTime +
                ", transferTime=" + transferTime +
                ", startCheckTime=" + startCheckTime +
                ", reason='" + reason + '\'' +
                ", state=" + state +
                '}';
    }

    public Instant getStartCheckTime() {
        return startCheckTime;
    }

    public WorkProcessTimeState getState() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkProcessKeyTime)) return false;
        WorkProcessKeyTime that = (WorkProcessKeyTime) o;
        return Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime) && Objects.equals(interruptTime, that.interruptTime) && Objects.equals(restartTime, that.restartTime) && Objects.equals(changeLineSetTime, that.changeLineSetTime) && Objects.equals(offlineTime, that.offlineTime) && Objects.equals(transferTime, that.transferTime) && Objects.equals(reason, that.reason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime, interruptTime, restartTime, changeLineSetTime, offlineTime, transferTime, reason);
    }

    @Override
    public WorkProcessKeyTime clone() {
        try {
            WorkProcessKeyTime clone = (WorkProcessKeyTime) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public Instant getInitTime() {
        return initTime;
    }
}
