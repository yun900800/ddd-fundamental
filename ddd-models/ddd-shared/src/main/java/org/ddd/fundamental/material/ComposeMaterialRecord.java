package org.ddd.fundamental.material;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@MappedSuperclass
public class ComposeMaterialRecord extends AbstractComposeComputable<MaterialRecordValueObject>{

    private double qty;

    @SuppressWarnings("unused")
    ComposeMaterialRecord(){
        super(new ArrayList<>());
    }

    public ComposeMaterialRecord(List<MaterialRecordValueObject> computableList) {
        super(computableList);
    }

    public ComposeMaterialRecord(ComposeMaterialRecord record1){
        this(record1.getComputableList());
    }


    @Override
    public ComposeMaterialRecord changeQty(double qty) {
        this.qty = qty;
        return this;
    }
}
