package org.ddd.fundamental.equipment.value;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.core.ValueObject;
import org.ddd.fundamental.equipment.ProductInput;
import org.ddd.fundamental.equipment.ProductOutput;
import org.ddd.fundamental.equipment.ProductResources;

import javax.persistence.*;


/**
 * 这个值对象可以进行重构
 */
@Embeddable
@MappedSuperclass
public class EquipmentMaster implements ValueObject, ProductResources, Cloneable {

    /**
     * 资产编号
     */
    private String assetNo;

    /**
     * 设备名称和描述
     */
    @AttributeOverrides({
            @AttributeOverride(name  = "name", column = @Column(name = "equipment_name", nullable = false)),
            @AttributeOverride(name  = "desc", column = @Column(name = "equipment_desc", nullable = false))
    })
    @Embedded
    private ChangeableInfo info;

    /**
     * 尺寸
     */
    @Embedded
    private EquipmentSize size;

    /**
     * 设备维护标准 选填
     */
    @Embedded
    private MaintainStandard standard;

    /**
     * 人员信息 选填
     */
    @Embedded
    private PersonInfo personInfo;

    /**
     * 质量信息 选填
     */
    @Embedded
    private QualityInfo qualityInfo;

    @SuppressWarnings("unused")
    EquipmentMaster() {
    }

    public static AssetNoStep newBuilder() {
        return new Builder();
    }

    @Override
    public EquipmentMaster clone() {
        try {
            EquipmentMaster clone = (EquipmentMaster) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public interface AssetNoStep {
        EquipmentInfoStep assetNo(String assetNo);
    }

    public interface EquipmentInfoStep {
        SizeStep info(ChangeableInfo info);
    }

    public interface SizeStep{
        MaintainStandardStep size(EquipmentSize size);

    }

    public interface MaintainStandardStep {
        PersonInfoStep standard(MaintainStandard standard);

        BuildStep noStandard();

        PersonInfoStep noStandardWithPerson();

        QualityInfoStep noStandardWithQuality();
    }

    public interface PersonInfoStep {
        QualityInfoStep personInfo(PersonInfo info);

        QualityInfoStep noPersonInfoWithQuality();
    }

    public interface QualityInfoStep {
        BuildStep qualityInfo(QualityInfo info);

        BuildStep noQualityInfo();
    }

    public interface BuildStep {
        EquipmentMaster build();
    }

    private static class Builder implements AssetNoStep, EquipmentInfoStep,
            SizeStep, MaintainStandardStep, PersonInfoStep,QualityInfoStep,
            BuildStep {

        /**
         * 资产编号
         */
        private String assetNo;

        /**
         * 设备名称
         */
        @AttributeOverrides({
                @AttributeOverride(name  = "name", column = @Column(name = "equipment_name", nullable = false)),
                @AttributeOverride(name  = "desc", column = @Column(name = "equipment_desc", nullable = false))
        })
        @Embedded
        private ChangeableInfo info;

        /**
         * 尺寸
         */
        @Embedded
        private EquipmentSize size;

        /**
         * 设备维护标准 选填
         */
        @Embedded
        private MaintainStandard standard;

        /**
         * 人员信息 选填
         */
        @Embedded
        private PersonInfo personInfo;

        /**
         * 质量信息 选填
         */
        @Embedded
        private QualityInfo qualityInfo;

        @Override
        public EquipmentInfoStep assetNo(String assetNo) {
            this.assetNo = assetNo;
            return this;
        }

        @Override
        public SizeStep info(ChangeableInfo info) {
            this.info = info;
            return this;
        }

        @Override
        public MaintainStandardStep size(EquipmentSize size) {
            this.size = size;
            return this;
        }

        @Override
        public PersonInfoStep standard(MaintainStandard standard) {
            this.standard = standard;
            return this;
        }

        @Override
        public BuildStep noStandard() {
            return this;
        }

        @Override
        public PersonInfoStep noStandardWithPerson() {
            return this;
        }

        @Override
        public QualityInfoStep noStandardWithQuality() {
            return this;
        }

        @Override
        public QualityInfoStep personInfo(PersonInfo info) {
            this.personInfo = info;
            return this;
        }

        @Override
        public QualityInfoStep noPersonInfoWithQuality() {
            return this;
        }


        @Override
        public BuildStep qualityInfo(QualityInfo info) {
            this.qualityInfo = info;
            return this;
        }

        @Override
        public BuildStep noQualityInfo() {
            return this;
        }

        @Override
        public EquipmentMaster build() {
            EquipmentMaster equipmentMaster = new EquipmentMaster();
            equipmentMaster.assetNo = assetNo;
            equipmentMaster.info = info;
            equipmentMaster.size = size;
            if (standard != null) {
                equipmentMaster.standard = standard;
            }
            if (personInfo != null) {
                equipmentMaster.personInfo = personInfo;
            }
            if (qualityInfo != null) {
                equipmentMaster.qualityInfo =qualityInfo;
            }
            return equipmentMaster;
        }
    }

    public ChangeableInfo getInfo() {
        return info.clone();
    }

    @Override
    public void input(ProductInput input) {
    }

    @Override
    public ProductOutput output() {
        return null;
    }

    @Override
    public String resourceName() {
        return info.getName();
    }

    public String getAssetNo() {
        return assetNo;
    }

    public EquipmentSize getSize() {
        return size.clone();
    }

    public MaintainStandard getStandard() {
        if (null != standard){
            return standard.clone();
        }
        return null;
    }

    public PersonInfo getPersonInfo() {
        if (null != personInfo) {
            return personInfo.clone();
        }
        return null;
    }

    public QualityInfo getQualityInfo() {
        if (null != qualityInfo) {
            return qualityInfo.clone();
        }
        return null;
    }

    @Override
    public String toString() {
        return "EquipmentMaster{" +
                "assetNo='" + assetNo + '\'' +
                ", info='" + info + '\'' +
                ", size=" + size +
                ", standard=" + standard +
                ", personInfo=" + personInfo +
                ", qualityInfo=" + qualityInfo +
                '}';
    }


}
