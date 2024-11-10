package org.ddd.fundamental.workprocess.value;

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
     * 目标良率
     */
    private double targetQualifiedRate;

    /**
     * 是否允许并行加工
     */
    private boolean isParallelProcessing;

    /**
     * 生产顺序
     */
    private int productOrder;

    /**
     * 是否进行批次管理
     */
    private boolean isBatchManage;

    /**
     * 报工规则
     */
    private String ReportWorkRule;

    @SuppressWarnings("unused")
    private WorkProcessController(){

    }

    private WorkProcessController(double targetQualifiedRate,
                                 boolean isParallelProcessing, int productOrder,
                                  boolean isBatchManage, String reportWorkRule) {
        this.targetQualifiedRate = targetQualifiedRate;
        this.isParallelProcessing = isParallelProcessing;
        this.productOrder = productOrder;
        this.isBatchManage = isBatchManage;
        this.ReportWorkRule = reportWorkRule;
    }

    public static WorkProcessController create(
            double targetQualifiedRate,
            boolean isParallelProcessing, int productOrder,
            boolean isBatchManage, String reportWorkRule
    ) {
        return new WorkProcessController(targetQualifiedRate,
                isParallelProcessing,productOrder,isBatchManage,reportWorkRule);
    }

    public double getTargetQualifiedRate() {
        return targetQualifiedRate;
    }

    public boolean isParallelProcessing() {
        return isParallelProcessing;
    }

    public int getProductOrder() {
        return productOrder;
    }

    public boolean isBatchManage() {
        return isBatchManage;
    }

    public String getReportWorkRule() {
        return ReportWorkRule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkProcessController)) return false;
        WorkProcessController that = (WorkProcessController) o;
        return Double.compare(that.targetQualifiedRate, targetQualifiedRate) == 0 && isParallelProcessing == that.isParallelProcessing && productOrder == that.productOrder && isBatchManage == that.isBatchManage && Objects.equals(ReportWorkRule, that.ReportWorkRule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetQualifiedRate, isParallelProcessing, productOrder, isBatchManage, ReportWorkRule);
    }

    @Override
    public String toString() {
        return "WorkProcessController{" +
                "targetQualifiedRate=" + targetQualifiedRate +
                ", isParallelProcessing=" + isParallelProcessing +
                ", productOrder=" + productOrder +
                ", isBatchManage=" + isBatchManage +
                ", ReportWorkRule='" + ReportWorkRule + '\'' +
                '}';
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
