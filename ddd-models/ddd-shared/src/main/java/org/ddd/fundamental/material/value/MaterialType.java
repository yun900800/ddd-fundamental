package org.ddd.fundamental.material.value;

public enum MaterialType {
    //产品的话可以是输入或者输出,也可以两者兼有
    PRODUCTION("产品或成品"),
    //如果物料属于在制品,那么需要添加一个必填属性,确定这个属性在设备中或者工序中是输入在制品,输出在制品,还是两者都可以
    WORKING_IN_PROGRESS("在制品"),
    // 原材料只能是输入原材料
    RAW_MATERIAL("原材料");

    private String value;

    MaterialType(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
