package org.ddd.fundamental.equipment.domain.model;

import org.ddd.fundamental.core.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@Embeddable
@MappedSuperclass
public class EquipmentMaster implements ValueObject {

    /**
     * 资产编号
     */
    private String assetNo;

    /**
     * 设备位置
     */
    private String location;

    /**
     * 尺寸
     */
    private String size;

    private MaintainStandard standard;

    @SuppressWarnings("unused")
    EquipmentMaster() {
    }

    public EquipmentMaster(String assetNo,String location, String size,
                           MaintainStandard standard ){
        this.assetNo = assetNo;
        this.location = location;
        this.size = size;
        this.standard = standard;
    }

    public String getAssetNo() {
        return assetNo;
    }

    public String getLocation() {
        return location;
    }

    public String getSize() {
        return size;
    }

    public MaintainStandard getStandard() {
        return standard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EquipmentMaster)) return false;
        EquipmentMaster that = (EquipmentMaster) o;
        return Objects.equals(assetNo, that.assetNo) && Objects.equals(location, that.location) && Objects.equals(size, that.size) && Objects.equals(standard, that.standard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assetNo, location, size, standard);
    }
}
