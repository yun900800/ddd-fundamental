package org.ddd.fundamental.factory.domain.model;

import org.ddd.fundamental.core.AbstractAggregateRoot;
import org.ddd.fundamental.factory.MachineShopId;
import org.ddd.fundamental.factory.ProductionLineId;
import org.ddd.fundamental.factory.value.MachineShopValueObject;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "f_machine_shop")
public class MachineShop extends AbstractAggregateRoot<MachineShopId> {

    private MachineShopValueObject machineShop;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "production_line_ids")
    private List<ProductionLineId> lines;

    @SuppressWarnings("unused")
    private MachineShop(){}

    public MachineShop(MachineShopValueObject machineShop){
        super(MachineShopId.randomId(MachineShopId.class));
        this.machineShop = machineShop;
        this.lines = new ArrayList<>();
    }

    public MachineShopValueObject getMachineShop() {
        return machineShop.clone();
    }

    private void defaultProductionLines(){
        if (null == this.lines){
            this.lines = new ArrayList<>();
        }
    }

    public MachineShop addLines(ProductionLineId lineId){
        defaultProductionLines();
        this.lines.add(lineId);
        return this;
    }

    public MachineShop removeLines(ProductionLineId lineId){
        defaultProductionLines();
        this.lines.remove(lineId);
        return this;
    }

    public MachineShop clearLines(){
        defaultProductionLines();
        this.lines.clear();
        return this;
    }

}
