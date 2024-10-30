package org.ddd.fundamental.factory.domain.model;

import org.ddd.fundamental.core.AbstractAggregateRoot;
import org.ddd.fundamental.factory.ProductionLineId;
import org.ddd.fundamental.factory.value.ProductionLineValueObject;

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

    @SuppressWarnings("unused")
    private ProductionLine(){}

    public ProductionLine(ProductionLineValueObject line){
        super(ProductionLineId.randomId(ProductionLineId.class));
        this.line = line;
    }

    public ProductionLineValueObject getLine() {
        return line.clone();
    }

    public List<WorkStation> getWorkStations() {
        return new ArrayList<>(workStations);
    }

    public ProductionLine addWorkStation(WorkStation station){
        this.workStations.add(station);
        return this;
    }

    public ProductionLine removeWorkStation(WorkStation station){
        this.workStations.remove(station);
        return this;
    }

    public ProductionLine clear(){
        this.workStations = new ArrayList<>();
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
}
