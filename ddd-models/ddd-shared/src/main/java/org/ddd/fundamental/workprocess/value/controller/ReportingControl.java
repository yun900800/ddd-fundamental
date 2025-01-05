package org.ddd.fundamental.workprocess.value.controller;

import org.ddd.fundamental.core.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

/**
 * 报工控制
 */
@MappedSuperclass
@Embeddable
public class ReportingControl implements ValueObject, Cloneable {

    /**
     * 是否允许批次报工
     */
    private boolean isReportingBatched;

    /**
     * 报工规则
     */
    private String reportWorkRule;

    @SuppressWarnings("unused")
    private ReportingControl(){}

    private ReportingControl(boolean isReportWorkBatched,
                             String reportWorkRule) {
        this.isReportingBatched = isReportWorkBatched;
        this.reportWorkRule = reportWorkRule;
    }

    public static ReportingControl create(boolean isReportWorkBatched,
                                          String reportWorkRule){
        return new ReportingControl(isReportWorkBatched,reportWorkRule);
    }

    public boolean isReportingBatched() {
        return isReportingBatched;
    }

    public String getReportWorkRule() {
        return reportWorkRule;
    }

    @Override
    public String toString() {
        return objToString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReportingControl)) return false;
        ReportingControl that = (ReportingControl) o;
        return isReportingBatched == that.isReportingBatched && Objects.equals(reportWorkRule, that.reportWorkRule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isReportingBatched, reportWorkRule);
    }

    @Override
    public ReportingControl clone() {
        try {
            ReportingControl clone = (ReportingControl) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
