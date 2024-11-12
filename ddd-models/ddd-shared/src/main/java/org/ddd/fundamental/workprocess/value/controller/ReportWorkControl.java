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
public class ReportWorkControl implements ValueObject, Cloneable {

    /**
     * 是否允许批次报工
     */
    private boolean isReportWorkBatched;

    /**
     * 报工规则
     */
    private String reportWorkRule;

    @SuppressWarnings("unused")
    private ReportWorkControl(){}

    private ReportWorkControl(boolean isReportWorkBatched,
                              String reportWorkRule) {
        this.isReportWorkBatched = isReportWorkBatched;
        this.reportWorkRule = reportWorkRule;
    }

    public static ReportWorkControl create(boolean isReportWorkBatched,
                                           String reportWorkRule){
        return new ReportWorkControl(isReportWorkBatched,reportWorkRule);
    }

    public boolean isReportWorkBatched() {
        return isReportWorkBatched;
    }

    public String getReportWorkRule() {
        return reportWorkRule;
    }

    @Override
    public String toString() {
        return "ReportWorkControl{" +
                "isReportWorkBatched=" + isReportWorkBatched +
                ", reportWorkRule='" + reportWorkRule + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReportWorkControl)) return false;
        ReportWorkControl that = (ReportWorkControl) o;
        return isReportWorkBatched == that.isReportWorkBatched && Objects.equals(reportWorkRule, that.reportWorkRule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isReportWorkBatched, reportWorkRule);
    }

    @Override
    public ReportWorkControl clone() {
        try {
            ReportWorkControl clone = (ReportWorkControl) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
