package org.ddd.fundamental.equipment.domain.model;

import org.ddd.fundamental.core.AbstractEntity;
import org.ddd.fundamental.equipment.value.ToolingEquipmentValue;
import org.ddd.fundamental.factory.EquipmentId;

import javax.persistence.*;

@Entity
@Table(name = "tooling_equipment")
public class ToolingEquipment extends AbstractEntity<EquipmentId> {

    private ToolingEquipmentValue toolingEquipmentInfo;

    /**
     * 资产编号
     */
    private String assetNo;

    /**
     * 设备唯一编码
     */
    private String assetCode;

    @Enumerated(EnumType.STRING)
    private EquipmentType equipmentType;

    @ManyToOne(fetch = FetchType.LAZY)
    private Equipment equipment;

    @SuppressWarnings("unused")
    private ToolingEquipment(){}

    public ToolingEquipment(ToolingEquipmentValue toolingEquipmentInfo,
                            String assetNo, String assetCode){
        super(EquipmentId.randomId(EquipmentId.class));
        this.toolingEquipmentInfo = toolingEquipmentInfo;
        this.assetNo = assetNo;
        this.assetCode = assetCode;
        this.equipmentType = EquipmentType.RESOURCE_TWO;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public ToolingEquipment enableUse(){
        this.toolingEquipmentInfo = this.toolingEquipmentInfo.enableUse();
        return this;
    }

    public ToolingEquipmentValue getToolingEquipmentInfo() {
        return toolingEquipmentInfo.clone();
    }

    public String getAssetNo() {
        return assetNo;
    }

    public String getAssetCode() {
        return assetCode;
    }

    @Override
    public String toString() {
        return "ToolingEquipment{" +
                "toolingEquipmentInfo=" + toolingEquipmentInfo +
                ", assetNo='" + assetNo + '\'' +
                ", assetCode='" + assetCode + '\'' +
                '}';
    }

    @Override
    public long created() {
        return 0;
    }
}
