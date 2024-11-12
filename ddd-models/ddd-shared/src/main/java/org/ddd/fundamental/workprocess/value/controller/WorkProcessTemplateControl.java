package org.ddd.fundamental.workprocess.value.controller;

import org.ddd.fundamental.core.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

/**
 * 工序控制 属性设置为基本类型会导致数据库生成不为空的检查
 */
@MappedSuperclass
@Embeddable
public class WorkProcessTemplateControl implements ValueObject, Cloneable {


    /**
     * 工序是否可以拆分
     */
    private boolean canSplit;

    /**
     * 是否允许前后工序间隔期
     */
    private GapRangeControl gapRangeControl;

    /**
     * 报工控制
     */
    private ReportWorkControl reportWorkControl;

    /**
     * 工单控制,这个属性是可选的
     */
    private WorkOrderControl workOrderControl;

    /**
     * 工序的输出是否需要检验
     */
    private boolean isAllowedChecked;

    /**
     * 是否允许并行加工
     */
    private boolean isParallelProcessing;

    /**
     * 工序生产顺序
     */
    private Integer processOrder;

    /**
     * 是否进行批次管理
     */
    private boolean isBatchManage;


    @SuppressWarnings("unused")
    private WorkProcessTemplateControl(){

    }

    private WorkProcessTemplateControl(boolean canSplit, GapRangeControl gapRangeControl,
                                       boolean isAllowedChecked, boolean isParallelProcessing,
                                       Integer processOrder, boolean isBatchManage,
                                       ReportWorkControl reportWorkControl,
                                       WorkOrderControl workOrderControl) {
        this.canSplit = canSplit;
        this.gapRangeControl = gapRangeControl;
        this.isAllowedChecked = isAllowedChecked;
        this.isParallelProcessing = isParallelProcessing;
        this.processOrder = processOrder;
        this.isBatchManage = isBatchManage;
        this.reportWorkControl = reportWorkControl;
        this.workOrderControl = workOrderControl;
    }

    public static class Builder {
        /**
         * 工序是否可以拆分
         */
        private boolean canSplit;

        /**
         * 是否允许前后工序间隔期
         */
        private GapRangeControl gapRangeControl;

        /**
         * 报工控制
         */
        private ReportWorkControl reportWorkControl;

        /**
         * 工单控制,这个属性是可选的
         */
        private WorkOrderControl workOrderControl;

        /**
         * 工序的输出是否需要检验
         */
        private boolean isAllowedChecked;

        /**
         * 是否允许并行加工
         */
        private boolean isParallelProcessing;

        /**
         * 工序生产顺序
         */
        private final Integer processOrder;

        /**
         * 是否进行批次管理
         */
        private final boolean isBatchManage;

        public Builder(Integer processOrder,Boolean isBatchManage){
            this.processOrder = processOrder;
            this.isBatchManage = isBatchManage;
        }

        public Builder canSplit(boolean canSplit){
            this.canSplit = canSplit;
            return this;
        }

        public Builder gapRangeControl(GapRangeControl gapRangeControl){
            this.gapRangeControl = gapRangeControl;
            return this;
        }

        public Builder reportWorkControl(ReportWorkControl reportWorkControl){
            this.reportWorkControl = reportWorkControl;
            return this;
        }

        public Builder workOrderControl(WorkOrderControl workOrderControl){
            this.workOrderControl = workOrderControl;
            return this;
        }

        public Builder isAllowedChecked(boolean isAllowedChecked){
            this.isAllowedChecked = isAllowedChecked;
            return this;
        }

        public Builder isParallelProcessing(boolean isParallelProcessing){
            this.isParallelProcessing = isParallelProcessing;
            return this;
        }

        public WorkProcessTemplateControl build() {
            return new WorkProcessTemplateControl(
                    canSplit,gapRangeControl,isAllowedChecked,
                    isParallelProcessing,processOrder,
                    isBatchManage, reportWorkControl,
                    workOrderControl
            );
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkProcessTemplateControl)) return false;
        WorkProcessTemplateControl that = (WorkProcessTemplateControl) o;
        return Objects.equals(canSplit, that.canSplit) && Objects.equals(gapRangeControl, that.gapRangeControl) && Objects.equals(reportWorkControl, that.reportWorkControl) && Objects.equals(workOrderControl, that.workOrderControl) && Objects.equals(isAllowedChecked, that.isAllowedChecked) && Objects.equals(isParallelProcessing, that.isParallelProcessing) && Objects.equals(processOrder, that.processOrder) && Objects.equals(isBatchManage, that.isBatchManage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(canSplit, gapRangeControl, reportWorkControl, workOrderControl, isAllowedChecked, isParallelProcessing, processOrder, isBatchManage);
    }

    public boolean getCanSplit() {
        return canSplit;
    }

    public GapRangeControl getGapRangeControl() {
        return gapRangeControl;
    }

    public ReportWorkControl getReportWorkControl() {
        return reportWorkControl;
    }

    public WorkOrderControl getWorkOrderControl() {
        return workOrderControl;
    }

    public boolean getAllowedChecked() {
        return isAllowedChecked;
    }

    public boolean getParallelProcessing() {
        return isParallelProcessing;
    }

    public Integer getProcessOrder() {
        return processOrder;
    }

    public boolean getBatchManage() {
        return isBatchManage;
    }

    @Override
    public String toString() {
        return "WorkProcessControl{" +
                "canSplit=" + canSplit +
                ", gapRangeControl=" + gapRangeControl +
                ", reportWorkControl=" + reportWorkControl +
                ", workOrderControl=" + workOrderControl +
                ", isAllowedChecked=" + isAllowedChecked +
                ", isParallelProcessing=" + isParallelProcessing +
                ", processOrder=" + processOrder +
                ", isBatchManage=" + isBatchManage +
                '}';
    }

    @Override
    public WorkProcessTemplateControl clone() {
        try {
            WorkProcessTemplateControl clone = (WorkProcessTemplateControl) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
