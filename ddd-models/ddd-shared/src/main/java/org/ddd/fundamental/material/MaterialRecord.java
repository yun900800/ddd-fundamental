package org.ddd.fundamental.material;

public class MaterialRecord extends ComputableObject {

    private MaterialRecordId materialRecordId;

    public MaterialRecord(String unit,double qty){
        super(unit,qty,QRCode.randomId(QRCode.class).toUUID());
        this.materialRecordId = MaterialRecordId.randomId(MaterialRecordId.class);
    }

    public MaterialRecord(MaterialRecord record) {
        super(record.getUnit(),record.getQty(),QRCode.randomId(QRCode.class).toUUID());
        this.materialRecordId = MaterialRecordId.randomId(MaterialRecordId.class);
    }


}
