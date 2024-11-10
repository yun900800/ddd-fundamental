package org.ddd.fundamental.workprocess.enums;

public enum ProductResourceType {
    TOOLING("工装"),
    DOCUMENT("文件"),
    MATERIAL("物料"),
    WORK_STATION("工位"),
    EQUIPMENT("设备"),
    PERSON("人员");

    private String value;
    ProductResourceType(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
