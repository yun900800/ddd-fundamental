package org.ddd.fundamental.workprocess.client;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.shared.api.equipment.EquipmentDTO;
import org.ddd.fundamental.shared.api.equipment.ToolingDTO;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class EquipmentClientFallback implements EquipmentClient{

    /**
     * 异常
     */
    private final Throwable cause;
    public EquipmentClientFallback(Throwable cause){
        this.cause = cause;
    }
    @Override
    public List<EquipmentDTO> equipments() {
        log.error("error is:{}",cause);
        return new ArrayList<>();
    }

    @Override
    public List<ToolingDTO> toolingList() {
        return new ArrayList<>();
    }
}
