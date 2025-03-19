package org.ddd.fundamental.material;


import org.ddd.fundamental.core.ValueObject;
import org.ddd.fundamental.factory.value.FactoryLocation;

import javax.persistence.*;

@Embeddable
@MappedSuperclass
public class MaterialRecordValue implements Computable, ValueObject {

    @Embedded
    MaterialMaster materialMaster;

    private double qty;

    @Embedded
    private FactoryLocation factoryLocation;

    private String qrCode;

    @SuppressWarnings("unused")
    MaterialRecordValue(){}

    public MaterialRecordValue(MaterialMaster master, double qty){
        this.materialMaster = master;
        this.qty = qty;
        this.qrCode = QRCode.randomId(QRCode.class).toUUID();
    }

    public MaterialRecordValue(MaterialRecordValue record) {
        this.materialMaster = record.materialMaster;
        this.qty = record.qty;
        this.qrCode = QRCode.randomId(QRCode.class).toUUID();
    }
    @Override
    public String getUnit() {
        return materialMaster.getUnit();
    }

    @Override
    public double getQty() {
        return qty;
    }

    @Override
    public String getQrCode() {
        return qrCode;
    }

    @Override
    public MaterialRecordValue changeQty(double qty) {
        this.qty = qty;
        return this;
    }

    public MaterialMaster getMaterialMaster() {
        return materialMaster;
    }
}
