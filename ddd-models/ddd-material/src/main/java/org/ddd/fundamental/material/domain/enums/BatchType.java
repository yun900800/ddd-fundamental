package org.ddd.fundamental.material.domain.enums;

public enum BatchType {


    INPUT_BATCH("输入批次"),
    OUTPUT_BATCH("输出批次"),
    PRODUCT_BATCH("生产批次"),
    ERP_BATCH("erp批次");

    private String value;

    BatchType(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
