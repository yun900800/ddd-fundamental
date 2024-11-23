package org.ddd.fundamental.workprocess.enums;

public enum ReportWorkType {
    OTHER("其他报工"),
    STANDARD("标准报工");

    private String value;
    ReportWorkType(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
