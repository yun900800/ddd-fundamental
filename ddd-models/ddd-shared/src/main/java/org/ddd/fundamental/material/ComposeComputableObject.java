package org.ddd.fundamental.material;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Embeddable
@MappedSuperclass
public class ComposeComputableObject<T extends ComputableObject> extends ComputableObject{

    private List<T> dataList;

    @SuppressWarnings("unused")
    ComposeComputableObject() {
    }

    public ComposeComputableObject(List<T> dataList){
        isCompose(dataList);
        super.unit = dataList.get(0).getUnit();
        super.qty = initQty(dataList);
        super.qrCode = QRCode.randomId(QRCode.class).toUUID();
        this.dataList = dataList;
    }

    private  boolean isCompose(List<T> dataList){
        Set<String> unitSets = new HashSet<>();
        for (ComputableObject object: dataList){
            unitSets.add(object.getUnit());
        }
        return unitSets.size() == 1;
    }

    private double initQty(List<T> dataList){
        double result = 0.0;
        for (ComputableObject object: dataList){
            result+=object.getQty();
        }
        return result;
    }

}
