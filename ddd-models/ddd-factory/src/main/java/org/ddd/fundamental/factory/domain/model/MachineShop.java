package org.ddd.fundamental.factory.domain.model;

import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.core.AbstractAggregateRoot;
import org.ddd.fundamental.factory.MachineShopId;
import org.ddd.fundamental.factory.ProductionLineId;
import org.ddd.fundamental.factory.value.MachineShopValueObject;
import org.hibernate.annotations.Type;
import org.springframework.util.StringUtils;

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
        changeUpdated();
        return this;
    }

    public MachineShop removeLines(ProductionLineId lineId){
        defaultProductionLines();
        this.lines.remove(lineId);
        changeUpdated();
        return this;
    }

    public MachineShop clearLines(){
        defaultProductionLines();
        this.lines.clear();
        changeUpdated();
        return this;
    }

    public MachineShop changeName(String name) {
        this.machineShop = this.machineShop.changeName(name);
        return this;
    }

    public MachineShop changeDesc(String desc) {
        this.machineShop = this.machineShop.changeDesc(desc);
        changeUpdated();
        return this;
    }

    public MachineShop enableUse(){
        this.machineShop = this.machineShop.enableUse();
        changeUpdated();
        return this;
    }

    public MachineShop disableUse(){
        this.machineShop = this.machineShop.disableUse();
        changeUpdated();
        return this;
    }

    public MachineShop changeMachineShop(ChangeableInfo shopInfo){
        if (StringUtils.hasLength(shopInfo.getName())) {
            this.changeName(shopInfo.getName());
        }
        if (StringUtils.hasLength(shopInfo.getDesc())) {
            this.changeDesc(shopInfo.getDesc());
        }
        if (this.machineShop.isUse()!= shopInfo.isUse()) {
            if (shopInfo.isUse()){
                this.enableUse();
            } else {
                this.disableUse();
            }
        }
        return this;
    }

    public List<ProductionLineId> getLines() {
        return new ArrayList<>(lines);
    }

    @Override
    public long created() {
        return 0;
    }
}
