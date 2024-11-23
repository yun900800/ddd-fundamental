package org.ddd.fundamental.workprocess.enums;

public enum BatchManagable {
        SERIAL("序列号"),
    BATCH_NO("否"),
    BATCH_YES("是");

    private String value;

    BatchManagable(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
