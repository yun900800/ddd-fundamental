package org.ddd.fundamental.factory.domain.model;

import org.ddd.fundamental.core.AbstractAggregateRoot;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.factory.ProductionLineId;
import org.ddd.fundamental.factory.value.ProductionLineValueObject;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "f_production_line")
public class ProductionLine extends AbstractAggregateRoot<ProductionLineId> {

    @Embedded
    private ProductionLineValueObject line;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "line_id", nullable = false)
    private List<WorkStation> workStations = new ArrayList<>();

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "equipment_ids")
    private List<EquipmentId> equipmentIds = new ArrayList<>();


    @SuppressWarnings("unused")
    private ProductionLine(){}

    public ProductionLine(ProductionLineValueObject line){
        super(ProductionLineId.randomId(ProductionLineId.class));
        this.line = line;
        this.equipmentIds = new ArrayList<>();
    }

    public ProductionLineValueObject getLine() {
        return line.clone();
    }

    public List<WorkStation> getWorkStations() {
        return new ArrayList<>(workStations);
    }

    public List<EquipmentId> getEquipmentIds() {
        return new ArrayList<>(equipmentIds);
    }

    private void defaultWorkStations(){
        if (null == this.workStations){
            this.workStations = new ArrayList<>();
        }
    }

    public ProductionLine addWorkStation(WorkStation station){
        defaultWorkStations();
        this.workStations.add(station);
        return this;
    }

    public ProductionLine removeWorkStation(WorkStation station){
        defaultWorkStations();
        this.workStations.remove(station);
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
