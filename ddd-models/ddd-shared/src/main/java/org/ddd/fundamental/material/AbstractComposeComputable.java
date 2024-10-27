package org.ddd.fundamental.material;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@MappedSuperclass
@TypeDef(
        name = "json",
        typeClass = JsonType.class
)
public abstract class AbstractComposeComputable<T extends Computable> implements Computable{


    @Type(type = "json")
    @Column(columnDefinition = "json" , name = "m_computable_list")
    private List<T> computableList;

    public AbstractComposeComputable(List<T> computableList){
        if (!isCompose(computableList)){
            throw new RuntimeException("单位不一致");
        }
        this.computableList = computableList;
    }

    private  boolean isCompose(List<T> dataList){
        Set<String> unitSets = new HashSet<>();
        for (Computable object: dataList){
            unitSets.add(object.getUnit());
        }
        return unitSets.size() == 1;
    }

    @Override
    public String getUnit() {
        return computableList.get(0).getUnit();
    }

    @Override
    public double getQty() {
        double qty = 0;
        for (Computable computable: computableList) {
            qty += computable.getQty();
        }
        return qty;
    }

    @Override
    public String getQrCode() {
        return QRCode.randomId(QRCode.class).toUUID();
    }

    public List<T> getComputableList() {
        return computableList;
    }
}
