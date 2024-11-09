package org.ddd.fundamental.material.domain.enums;

public enum BatchHandleType {
    INPUT_BATCH("输入批次"),
    OUTPUT_BATCH("输出批次");


    private String value;

    BatchHandleType(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
