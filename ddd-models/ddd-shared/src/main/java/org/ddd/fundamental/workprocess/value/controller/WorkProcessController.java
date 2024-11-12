package org.ddd.fundamental.workprocess.value.controller;

import org.ddd.fundamental.core.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

/**
 * 工序控制
 */
@MappedSuperclass
@Embeddable
public class WorkProcessController implements ValueObject, Cloneable {


    /**
     * 工序是否可以拆分
     */
    private boolean canSplit;

    /**
     * 是否允许前后工序间隔期
     */
    private GapRangeControl isGapRangeControl;

    /**
     * 报工控制
     */
    private ReportWorkControl reportWorkControl;

    /**
     * 工单控制
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
    private WorkProcessController(){

    }

    private WorkProcessController(boolean canSplit, GapRangeControl isGapRangeControl,
                                 boolean isAllowedChecked, boolean isParallelProcessing,
                                 Integer processOrder, boolean isBatchManage) {
        this.canSplit = canSplit;
        this.isGapRangeControl = isGapRangeControl;
        this.isAllowedChecked = isAllowedChecked;
        this.isParallelProcessing = isParallelProcessing;
        this.processOrder = processOrder;
        this.isBatchManage = isBatchManage;
    }

    public static WorkProcessController create(boolean canSplit, GapRangeControl isGapRangeControl,
                                               boolean isAllowedChecked, boolean isParallelProcessing,
                                               int productOrder, boolean isBatchManage
    ) {
        return new WorkProcessController(
                canSplit,isGapRangeControl,isAllowedChecked, isParallelProcessing,productOrder,isBatchManage);
    }

    @Override
    public String toString() {
        return "WorkProcessController{" +
                "canSplit=" + canSplit +
                ", isGapRangeControl=" + isGapRangeControl +
                ", isAllowedChecked=" + isAllowedChecked +
                ", isParallelProcessing=" + isParallelProcessing +
                ", processOrder=" + processOrder +
                ", isBatchManage=" + isBatchManage +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkProcessController)) return false;
        WorkProcessController that = (WorkProcessController) o;
        return canSplit == that.canSplit && isAllowedChecked == that.isAllowedChecked && isParallelProcessing == that.isParallelProcessing && processOrder == that.processOrder && isBatchManage == that.isBatchManage && Objects.equals(isGapRangeControl, that.isGapRangeControl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(canSplit, isGapRangeControl, isAllowedChecked, isParallelProcessing, processOrder, isBatchManage);
    }

    public boolean isCanSplit() {
        return canSplit;
    }

    public GapRangeControl getIsGapRangeControl() {
        return isGapRangeControl;
    }

    public boolean isAllowedChecked() {
        return isAllowedChecked;
    }

    public boolean isParallelProcessing() {
        return isParallelProcessing;
    }

    public int getProcessOrder() {
        return processOrder;
    }

    public boolean isBatchManage() {
        return isBatchManage;
    }

    @Override
    public WorkProcessController clone() {
        try {
            WorkProcessController clone = (WorkProcessController) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
