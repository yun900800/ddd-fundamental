package org.ddd.fundamental.material.domain.enums;

/**
 * 物料等级
 */
public enum MaterialLevel {



    KEY_MATERIAL("关键物料"),

    IMPORTANT_MATERIAL("重要物料"),

    COMMON_MATERIAL("普通物料");

    private String desc;

    public String getDesc() {
        return desc;
    }

    MaterialLevel(String desc){
        this.desc = desc;
    }
}
