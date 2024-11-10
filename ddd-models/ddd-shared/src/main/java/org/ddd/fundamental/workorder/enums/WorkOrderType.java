package org.ddd.fundamental.workorder.enums;

public enum WorkOrderType {
    PRODUCT_WORK_ORDER("生产工单"),
    REWORK_WORK_ORDER("返工工单"),
    INDIRECT_COST_WORK_ORDER("间接成本工单"),
    TRANSFER_WORK_ORDER("运输工单"),
    MAINTAIN_WORK_ORDER("维护工单");

    private String value;

    private WorkOrderType(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
