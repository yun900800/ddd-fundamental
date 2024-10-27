package org.ddd.fundamental.material;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@MappedSuperclass
public class ComposeMaterialRecord1 extends AbstractComposeComputable<MaterialRecord1>{

    private double qty;

    @SuppressWarnings("unused")
    ComposeMaterialRecord1(){
        super(new ArrayList<>());
    }

    public ComposeMaterialRecord1(List<MaterialRecord1> computableList) {
        super(computableList);
    }

    public ComposeMaterialRecord1(ComposeMaterialRecord1 record1){
        this(record1.getComputableList());
    }


    @Override
    public ComposeMaterialRecord1 changeQty(double qty) {
        this.qty = qty;
        return this;
    }
}
