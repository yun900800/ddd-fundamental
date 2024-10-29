package org.ddd.fundamental.equipment.value;

import org.ddd.fundamental.core.ValueObject;
import org.ddd.fundamental.equipment.ProductInput;
import org.ddd.fundamental.equipment.ProductOutput;
import org.ddd.fundamental.equipment.ProductResources;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

@Embeddable
@MappedSuperclass
public class EquipmentMaster implements ValueObject, ProductResources {

    /**
     * 资产编号
     */
    private String assetNo;

    /**
     * 设备名称
     */
    private String equipmentName;

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

    interface AssetNoStep {
        EquipmentNameStep assetNo(String assetNo);
    }

    interface EquipmentNameStep{
        SizeStep name(String equipmentName);
    }

    interface SizeStep{
        MaintainStandardStep size(EquipmentSize size);

    }

    interface MaintainStandardStep {
        PersonInfoStep standard(MaintainStandard standard);

        BuildStep noStandard();

        PersonInfoStep noStandardWithPerson();

        QualityInfoStep noStandardWithQuality();
    }

    interface PersonInfoStep {
        QualityInfoStep personInfo(PersonInfo info);

        BuildStep noPersonInfo();
    }

    interface QualityInfoStep {
        BuildStep qualityInfo(QualityInfo info);

        BuildStep noQualityInfo();
    }

    interface BuildStep {
        EquipmentMaster build();
    }

    private static class Builder implements AssetNoStep,EquipmentNameStep,
            SizeStep, MaintainStandardStep, PersonInfoStep,QualityInfoStep,
            BuildStep {

        /**
         * 资产编号
         */
        private String assetNo;

        /**
         * 设备名称
         */
        private String equipmentName;

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
        public EquipmentNameStep assetNo(String assetNo) {
            this.assetNo = assetNo;
            return this;
        }

        @Override
        public SizeStep name(String equipmentName) {
            this.equipmentName = equipmentName;
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
        public BuildStep noPersonInfo() {
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
            equipmentMaster.equipmentName = equipmentName;
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


    @Override
    public void input(ProductInput input) {
    }

    @Override
    public ProductOutput output() {
        return null;
    }

    @Override
    public String resourceName() {
        return equipmentName;
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
                ", equipmentName='" + equipmentName + '\'' +
                ", size=" + size +
                ", standard=" + standard +
                ", personInfo=" + personInfo +
                ", qualityInfo=" + qualityInfo +
                '}';
    }
}
