package org.ddd.fundamental.factory.domain.model;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.core.AbstractAggregateRoot;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.factory.ProductionLineId;
import org.ddd.fundamental.factory.value.ProductionLineValue;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "f_production_line")
@Slf4j
public class ProductionLine extends AbstractAggregateRoot<ProductionLineId> {

    @Embedded
    private ProductionLineValue line;

    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.LAZY,
            mappedBy = "line")
    //@JoinColumn(name = "line_id", nullable = false)
    private List<WorkStation> workStations = new ArrayList<>();

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "equipment_ids")
    private List<EquipmentId> equipmentIds = new ArrayList<>();


    @SuppressWarnings("unused")
    protected ProductionLine(){}

    public ProductionLine(ProductionLineValue line){
        super(ProductionLineId.randomId(ProductionLineId.class));
        this.line = line;
        this.equipmentIds = new ArrayList<>();
    }

    public ProductionLine changeName(String name) {
        this.line = this.line.changeName(name);
        return this;
    }

    public ProductionLine changeDesc(String desc) {
        this.line = this.line.changeDesc(desc);
        return this;
    }

    public ProductionLine enableLine(){
        this.line = this.line.enableLine();
        return this;
    }

    public ProductionLine disableLine() {
        this.line = this.line.disableLine();
        return this;
    }

    public ProductionLineValue getLine() {
        return line.clone();
    }

    public List<WorkStation> getWorkStations() {
        return workStations;
    }

    public List<EquipmentId> getEquipmentIds() {
        return equipmentIds;
    }

    private void defaultWorkStations(){
        if (null == this.workStations){
            this.workStations = new ArrayList<>();
        }
    }

    public ProductionLine addWorkStation(WorkStation station){
        defaultWorkStations();
        this.workStations.add(station);
        //这里为什么还要去查询一下子表
        station.setLine(this);
        return this;
    }

    public ProductionLine removeWorkStation(WorkStation station){
        defaultWorkStations();
        this.workStations.remove(station);
        station.setLine(null);
        return this;
    }

    public ProductionLine clearWorkStations(){
        defaultWorkStations();
        this.workStations = new ArrayList<>();
        return this;
    }

    /**
     * 解决查询出来的数据为NULL的情况
     */
    private void defaultEquipmentIds(){
        if (null == this.equipmentIds){
            this.equipmentIds = new ArrayList<>();
        }
    }

    public ProductionLine addEquipment(EquipmentId id){
        defaultEquipmentIds();
        this.equipmentIds.add(id);
        return this;
    }

    public ProductionLine removeEquipment(EquipmentId id){
        defaultEquipmentIds();
        this.equipmentIds.remove(id);
        return this;
    }

    public ProductionLine clearEquipments(){
        defaultEquipmentIds();
        this.equipmentIds.clear();
        return this;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductionLine)) return false;
        if (!super.equals(o)) return false;
        ProductionLine that = (ProductionLine) o;
        return Objects.equals(id(), that.id());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id());
    }

    @Override
    public String toString() {
        return "ProductionLine{" +
                "line=" + line +
                ", workStations=" + workStations +
                '}';
    }

    @Override
    public long created() {
        return 0;
    }
}
