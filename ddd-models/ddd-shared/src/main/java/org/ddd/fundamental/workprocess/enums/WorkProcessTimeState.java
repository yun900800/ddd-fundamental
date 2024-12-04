package org.ddd.fundamental.workprocess.enums;

public enum WorkProcessTimeState {

    LINE_CHANGED("换线中"),

    WORK_PROCESS_RUNNING("工序运行中"),

    WORK_PROCESS_INTERRUPTED("工序中断"),

    WORK_PROCESS_CHECKED("工序检查中"),

    WORK_PROCESS_FINISHED("工序结束"),

    WORK_PROCESS_OFFLINE("工序下线"),

    WORK_PROCESS_TRANSFER("工序设备运输"),

    WORK_PROCESS_TRANSFER_OVER("工序设备运输结束"),

    INIT("工单工序初始化");


    private String value;

    WorkProcessTimeState(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
