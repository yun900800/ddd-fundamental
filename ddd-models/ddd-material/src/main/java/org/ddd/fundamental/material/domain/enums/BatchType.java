package org.ddd.fundamental.material.domain.enums;

public enum BatchType {
    PRODUCT_BATCH("生产批次"),
    ERP_BATCH("erp批次");

    private String value;

    private BatchType(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
