package org.ddd.fundamental.shared.api.material.enums;

public enum MaterialType {
    PRODUCTION("产品或成品"),
    WORKING_IN_PROGRESS("在制品"),
    RAW_MATERIAL("原材料");

    private String value;

    MaterialType(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
