package org.ddd.fundamental.workprocess.value.controller;

import org.ddd.fundamental.core.ValueObject;
import org.ddd.fundamental.workorder.enums.WorkOrderType;
import org.ddd.fundamental.workprocess.enums.BatchManagable;
import org.ddd.fundamental.workprocess.enums.ReportWorkType;
import org.springframework.util.StringUtils;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.Objects;


/**
 * 工序控制属性类
 * 这里对每个控制属性进行分组
 * 1、工单类型
 * 2、排产
 * 3、报工
 * 4、是否上传ERP
 * 5、工序拆分
 * 6、批次管理
 * 7、超欠交控制
 * 8、记录废品原因
 * 9、后工序同步
 * 10、强制检验
 *
 * 不同的工单类型影响报工,比如有些工单不需要报工
 */
@MappedSuperclass
@Embeddable
public class WorkProcessControl implements ValueObject, Cloneable {

    /**
     * 是否自动排产
     */
    private boolean autoSchedule;

    /**
     * 工单类型(必填)
     */
    private WorkOrderType workOrderType;

    /**
     * 工序处理顺序(必填)
     */
    private int processOrder;

    /**
     * 是否进行报工
     */
    private boolean isReportWork;

    /**
     * 报工类型
     */
    private ReportWorkType reportWorkType;

    /**
     * 报工数据
     */
    private String reportData;

    /**
     * 是否允许分批报工
     */
    private boolean isBatchReport;

    /**
     * 分批报工数量
     */
    private double batchQty;

    /**
     * 是否上传ERP
     */
    private boolean isUploadERP;

    /**
     * 工序是否可以拆分，也就是并行加工
     */
    private boolean canSplit;

    /**
     * 是否批次管理
     */
    private BatchManagable batchManagable;

    /**
     * 是否进行超交或者欠交管理
     */
    private boolean isOverOrOweManagable;

    /**
     * 超交或者欠交比例
     */
    private double percent;

    /**
     * 是否满量自动结束
     */
    private boolean fullAutoOver;

    /**
     * 是否记录废品原因
     */
    private boolean recordReason;

    /**
     * 后工序同步间隔分钟数
     */
    private double nextProcessSyncMinutes;

    /**
     * 是否进行强制检验
     */
    private boolean isForceChecked;

    private WorkProcessControl(boolean autoSchedule, WorkOrderType workOrderType,
                              int processOrder, boolean isReportWork,
                              ReportWorkType reportWorkType,
                              String reportData, boolean isBatchReport,
                              double batchQty, boolean isUploadERP,
                              boolean canSplit, BatchManagable batchManagable,
                              boolean isOverOrOweManagable, double percent,
                              boolean fullAutoOver, boolean recordReason,
                              double nextProcessSyncMinutes, boolean isForceChecked) {
        this.autoSchedule = autoSchedule;
        this.workOrderType = workOrderType;
        this.processOrder = processOrder;
        this.isReportWork = isReportWork;
        this.reportWorkType = reportWorkType;
        this.reportData = reportData;
        this.isBatchReport = isBatchReport;
        this.batchQty = batchQty;
        this.isUploadERP = isUploadERP;
        this.canSplit = canSplit;
        this.batchManagable = batchManagable;
        this.isOverOrOweManagable = isOverOrOweManagable;
        this.percent = percent;
        this.fullAutoOver = fullAutoOver;
        this.recordReason = recordReason;
        this.nextProcessSyncMinutes = nextProcessSyncMinutes;
        this.isForceChecked = isForceChecked;
    }

    @SuppressWarnings("unused")
    protected WorkProcessControl(){
    }

    @Override
    public WorkProcessControl clone() {
        try {
            WorkProcessControl clone = (WorkProcessControl) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public static class Builder {
        /**
         * 工单类型(必填)
         */
        private final WorkOrderType workOrderType;

        /**
         * 工序处理顺序(必填)
         */
        private final int processOrder;

        /**
         * 是否自动排产
         */
        private boolean autoSchedule;

        /**
         * 是否进行报工
         */
        private boolean isReportWork;

        /**
         * 报工类型
         */
        private ReportWorkType reportWorkType;

        /**
         * 报工数据
         */
        private String reportData;

        /**
         * 是否允许分批报工
         */
        private boolean isBatchReport;

        /**
         * 分批报工数量
         */
        private double batchQty;

        /**
         * 是否上传ERP
         */
        private boolean isUploadERP;

        /**
         * 工序是否可以拆分，也就是并行加工
         */
        private boolean canSplit;

        /**
         * 是否批次管理
         */
        private BatchManagable batchManagable;

        /**
         * 是否进行超交或者欠交管理
         */
        private boolean isOverOrOweManagable;

        /**
         * 超交或者欠交比例
         */
        private double percent;

        /**
         * 是否满量自动结束
         */
        private boolean fullAutoOver;

        /**
         * 是否记录废品原因
         */
        private boolean recordReason;

        /**
         * 后工序同步间隔分钟数
         */
        private double nextProcessSyncMinutes;

        /**
         * 是否进行强制检验
         */
        private boolean isForceChecked;

        public Builder(WorkOrderType workOrderType,int processOrder,
                       boolean isForceChecked){
            this.workOrderType = workOrderType;
            this.processOrder = processOrder;
            this.isForceChecked = isForceChecked;
        }

        public Builder autoSchedule(boolean autoSchedule){
            this.autoSchedule = autoSchedule;
            return this;
        }

        public Builder isReportWork(boolean isReportWork){
            this.isReportWork = isReportWork;
            return this;
        }

        public Builder reportWorkType(ReportWorkType reportWorkType){
            this.reportWorkType = reportWorkType;
            return this;
        }

        public Builder reportData(String reportData){
            this.reportData = reportData;
            return this;
        }

        public Builder isBatchReport(boolean isBatchReport){
            this.isBatchReport = isBatchReport;
            return this;
        }

        public Builder batchQty(double batchQty){
            this.batchQty = batchQty;
            return this;
        }

        public Builder isUploadERP(boolean isUploadERP){
            this.isUploadERP = isUploadERP;
            return this;
        }

        public Builder canSplit(boolean canSplit){
            this.canSplit = canSplit;
            return this;
        }

        public Builder batchManagable(BatchManagable batchManagable){
            this.batchManagable = batchManagable;
            return this;
        }

        public Builder isOverOrOweManagable(boolean isOverOrOweManagable){
            this.isOverOrOweManagable = isOverOrOweManagable;
            if (!this.isOverOrOweManagable) {
                this.fullAutoOver = true;
            }
            return this;
        }

        public Builder percent(double percent){
            this.percent = percent;
            return this;
        }

        public Builder fullAutoOver(boolean fullAutoOver){
            this.fullAutoOver = fullAutoOver;
            return this;
        }

        public Builder recordReason(boolean recordReason){
            this.recordReason = recordReason;
            return this;
        }

        public Builder nextProcessSyncMinutes(double nextProcessSyncMinutes){
            this.nextProcessSyncMinutes = nextProcessSyncMinutes;
            return this;
        }

        /**
         * 验证是否合法
         */
        private void validate(){
            if (WorkOrderType.TRANSFER_WORK_ORDER.equals(workOrderType) && isReportWork){
                throw new RuntimeException("运输工单不需要报工");
            }
            if (!isReportWork && (reportWorkType != null || StringUtils.hasLength(reportData)
                || isBatchReport || batchQty > 0)) {
                throw new RuntimeException("不需要报工的控制信息存在报工数据");
            }
            if (isOverOrOweManagable && percent == 0) {
                throw new RuntimeException("超欠交控制需要设置一个不为0的比例");
            }
            if (isOverOrOweManagable && fullAutoOver) {
                throw new RuntimeException("超欠交控制不能自动结束");
            }

            if (!isOverOrOweManagable && percent > 0) {
                throw new RuntimeException("不需要超欠交控制无需设置大于0的比例");
            }
            if (!isOverOrOweManagable && !fullAutoOver) {
                this.fullAutoOver = true;
            }
        }

        public WorkProcessControl build(){
            validate();
            return new WorkProcessControl(
                  autoSchedule,workOrderType,
                  processOrder,isReportWork,
                  reportWorkType,reportData,
                  isBatchReport,batchQty,
                  isUploadERP,canSplit,
                  batchManagable,isOverOrOweManagable,
                  percent,fullAutoOver,
                  recordReason,nextProcessSyncMinutes,
                  isForceChecked
            );
        }
    }

    @Override
    public String toString() {
        return objToString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkProcessControl)) return false;
        WorkProcessControl control = (WorkProcessControl) o;
        return autoSchedule == control.autoSchedule && processOrder == control.processOrder &&
                isReportWork == control.isReportWork && isBatchReport == control.isBatchReport &&
                Double.compare(control.batchQty, batchQty) == 0 && isUploadERP == control.isUploadERP &&
                canSplit == control.canSplit && isOverOrOweManagable == control.isOverOrOweManagable && Double.compare(control.percent, percent) == 0 && fullAutoOver == control.fullAutoOver && recordReason == control.recordReason && Double.compare(control.nextProcessSyncMinutes, nextProcessSyncMinutes) == 0 && isForceChecked == control.isForceChecked && workOrderType == control.workOrderType && reportWorkType == control.reportWorkType && Objects.equals(reportData, control.reportData) && batchManagable == control.batchManagable;
    }

    @Override
    public int hashCode() {
        return Objects.hash(autoSchedule, workOrderType, processOrder, isReportWork,
                reportWorkType, reportData, isBatchReport, batchQty, isUploadERP,
                canSplit, batchManagable, isOverOrOweManagable, percent, fullAutoOver,
                recordReason, nextProcessSyncMinutes, isForceChecked);
    }

    public boolean isAutoSchedule() {
        return autoSchedule;
    }

    public WorkOrderType getWorkOrderType() {
        return workOrderType;
    }

    public int getProcessOrder() {
        return processOrder;
    }

    public boolean isReportWork() {
        return isReportWork;
    }

    public ReportWorkType getReportWorkType() {
        return reportWorkType;
    }

    public String getReportData() {
        return reportData;
    }

    public boolean isBatchReport() {
        return isBatchReport;
    }

    public double getBatchQty() {
        return batchQty;
    }

    public boolean isUploadERP() {
        return isUploadERP;
    }

    public boolean isCanSplit() {
        return canSplit;
    }

    public BatchManagable getBatchManagable() {
        return batchManagable;
    }

    public boolean isOverOrOweManagable() {
        return isOverOrOweManagable;
    }

    public double getPercent() {
        return percent;
    }

    public boolean isFullAutoOver() {
        return fullAutoOver;
    }

    public boolean isRecordReason() {
        return recordReason;
    }

    public double getNextProcessSyncMinutes() {
        return nextProcessSyncMinutes;
    }

    public boolean isForceChecked() {
        return isForceChecked;
    }
}
