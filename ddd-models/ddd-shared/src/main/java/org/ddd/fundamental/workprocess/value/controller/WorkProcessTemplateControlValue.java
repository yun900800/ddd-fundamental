package org.ddd.fundamental.workprocess.value.controller;

import org.ddd.fundamental.core.ValueObject;
import org.ddd.fundamental.workprocess.enums.BatchManagable;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

/**
 * 工序控制 属性设置为基本类型会导致数据库生成不为空的检查
 * 哪些工序属性可以提取到工序模板?
 * 1、工序拆分
 * 2、是否进行报工控制
 * 3、工序输出是否检验
 * 4、后工序同步间隔分钟数
 * 一个对象属性太多怎么设置好的api
 */
@MappedSuperclass
@Embeddable
public class WorkProcessTemplateControlValue implements ValueObject, Cloneable {


    /**
     * 工序是否可以拆分
     */
    private boolean canSplit;

    /**
     * 报工控制
     */
    private ReportingControl reportWorkControl;

    /**
     * 工序的输出是否需要检验
     */
    private boolean isAllowedChecked;

    /**
     * 工序生产顺序
     */
    private Integer processOrder;

    /**
     * 批次管理
     */
    @Enumerated(EnumType.STRING)
    private BatchManagable batchManagable;

    /**
     * 后工序同步间隔分钟数
     */
    private double nextProcessSyncMinutes;


    @SuppressWarnings("unused")
    private WorkProcessTemplateControlValue(){}

    private WorkProcessTemplateControlValue(boolean canSplit, double nextProcessSyncMinutes,
                                            boolean isAllowedChecked,
                                            Integer processOrder, BatchManagable batchManagable,
                                            ReportingControl reportWorkControl) {
        this.canSplit = canSplit;
        this.isAllowedChecked = isAllowedChecked;
        this.processOrder = processOrder;
        this.batchManagable = batchManagable;
        this.reportWorkControl = reportWorkControl;
        this.nextProcessSyncMinutes = nextProcessSyncMinutes;
    }

    public static class Builder {
        /**
         * 工序是否可以拆分
         */
        private boolean canSplit;

        /**
         * 报工控制
         */
        private ReportingControl reportWorkControl;


        /**
         * 工序的输出是否需要检验
         */
        private boolean isAllowedChecked;

        /**
         * 工序生产顺序
         */
        private final Integer processOrder;

        /**
         * 批次管理
         */
        private final BatchManagable batchManagable;

        /**
         * 后工序同步间隔分钟数
         */
        private double nextProcessSyncMinutes;

        public Builder(Integer processOrder,BatchManagable batchManagable){
            this.processOrder = processOrder;
            this.batchManagable = batchManagable;
        }

        public Builder canSplit(boolean canSplit){
            this.canSplit = canSplit;
            return this;
        }


        public Builder reportWorkControl(ReportingControl reportWorkControl){
            this.reportWorkControl = reportWorkControl;
            return this;
        }

        public Builder isAllowedChecked(boolean isAllowedChecked){
            this.isAllowedChecked = isAllowedChecked;
            return this;
        }

        public Builder nextProcessSyncMinutes(double nextProcessSyncMinutes){
            this.nextProcessSyncMinutes = nextProcessSyncMinutes;
            return this;
        }

        public WorkProcessTemplateControlValue build() {
            return new WorkProcessTemplateControlValue(
                    canSplit, nextProcessSyncMinutes,
                    isAllowedChecked,processOrder,
                    batchManagable, reportWorkControl
            );
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkProcessTemplateControlValue)) return false;
        WorkProcessTemplateControlValue that = (WorkProcessTemplateControlValue) o;
        return Objects.equals(canSplit, that.canSplit) &&
                Objects.equals(reportWorkControl, that.reportWorkControl) &&
                Objects.equals(isAllowedChecked, that.isAllowedChecked) &&
                Objects.equals(processOrder, that.processOrder) &&
                Objects.equals(batchManagable, that.batchManagable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(canSplit,reportWorkControl,
                 isAllowedChecked,
                processOrder, batchManagable);
    }

    /**
     * 禁止工序可以拆分
     * @return
     */
    public WorkProcessTemplateControlValue disableSplit() {
        this.canSplit = false;
        return this;
    }

    /**
     * 允许工序可以拆分
     * @return
     */
    public WorkProcessTemplateControlValue enableSplit(){
        this.canSplit = true;
        return this;
    }

    public WorkProcessTemplateControlValue allowChecked(){
        this.isAllowedChecked = true;
        return this;
    }

    public WorkProcessTemplateControlValue notAllowChecked(){
        this.isAllowedChecked = false;
        return this;
    }

    public boolean getCanSplit() {
        return canSplit;
    }

    public ReportingControl getReportWorkControl() {
        return reportWorkControl;
    }

    public boolean getAllowedChecked() {
        return isAllowedChecked;
    }

    public Integer getProcessOrder() {
        return processOrder;
    }

    public BatchManagable getBatchManagable() {
        return batchManagable;
    }

    public boolean isCanSplit() {
        return canSplit;
    }

    public boolean isAllowedChecked() {
        return isAllowedChecked;
    }

    public double getNextProcessSyncMinutes() {
        return nextProcessSyncMinutes;
    }

    @Override
    public String toString() {
        return "WorkProcessControl{" +
                "canSplit=" + canSplit +
                ", reportWorkControl=" + reportWorkControl +
                ", isAllowedChecked=" + isAllowedChecked +
                ", processOrder=" + processOrder +
                ", batchManagable=" + batchManagable +
                '}';
    }

    @Override
    public WorkProcessTemplateControlValue clone() {
        try {
            WorkProcessTemplateControlValue clone = (WorkProcessTemplateControlValue) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
