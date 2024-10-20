package org.ddd.fundamental.material;

public class MaterialRecord extends ComputableObject<MaterialRecord> {

    private MaterialRecordId materialRecordId;

    public MaterialRecord(String unit,double qty){
        super(unit,qty,QRCode.randomId(QRCode.class).toUUID());
        this.materialRecordId = MaterialRecordId.randomId(MaterialRecordId.class);
    }


}
