package org.ddd.fundamental.material;


import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;

@Embeddable
@MappedSuperclass
public class MaterialRecord1 implements Computable {

    private String unit;

    private double qty;

    private String qrCode;

    @SuppressWarnings("unused")
    MaterialRecord1(){}

    public MaterialRecord1(String unit, double qty){
        this.unit = unit;
        this.qty = qty;
        this.qrCode = QRCode.randomId(QRCode.class).toUUID();
    }

    public MaterialRecord1(MaterialRecord1 record) {
        this.unit = record.unit;
        this.qty = record.qty;
        this.qrCode = QRCode.randomId(QRCode.class).toUUID();
    }
    @Override
    public String getUnit() {
        return unit;
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
    public MaterialRecord1 changeQty(double qty) {
        this.qty = qty;
        return this;
    }


}
