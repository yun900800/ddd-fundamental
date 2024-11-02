package org.ddd.fundamental.equipment.domain.value;

public enum EquipmentStatus {

    FAULT("故障中"),

    USED("已使用"),

    REPAIR("维修中"),

    MAINTAINED("保养中"),

    LOCKED("锁定"),

    ENABLED("可用");
    private String status;

    EquipmentStatus(String status){
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
