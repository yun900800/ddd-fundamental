package org.ddd.fundamental.constants;

public enum ItemType {

    RAW_MATERIAL("rawMaterial","原材料"),

    WORK_IN_PROGRESS("wrokInProgress","在制品"),

    FINISHED_PRODUCT("finishedProduct","成品");

    private String key;

    private String name;

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    private ItemType(String key, String name){
        this.name = name;
        this.key = key;
    }
}
