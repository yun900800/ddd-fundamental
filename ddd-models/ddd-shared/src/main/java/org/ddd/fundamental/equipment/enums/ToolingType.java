package org.ddd.fundamental.equipment.enums;

public enum ToolingType {
    GZ("工装"),
    JJ("夹具"),
    SB("设备")
    ;

    private String name;

    private ToolingType(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
