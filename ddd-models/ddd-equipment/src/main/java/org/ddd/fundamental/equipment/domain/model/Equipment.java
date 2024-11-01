package org.ddd.fundamental.equipment.domain.model;

import org.ddd.fundamental.core.AbstractAggregateRoot;
import org.ddd.fundamental.core.DomainObjectId;
import org.ddd.fundamental.day.YearModelValueObject;
import org.ddd.fundamental.day.range.DateRange;
import org.ddd.fundamental.equipment.value.EquipmentMaster;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.tuple.TwoTuple;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "equipment")
public class Equipment extends AbstractAggregateRoot<EquipmentId> {

    private EquipmentType equipmentType;

    @Embedded
    private YearModelValueObject model;


    @Embedded
    private EquipmentMaster master;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name="equipment_dates",
            joinColumns=@JoinColumn(name="equipment_id")
    )
    private Set<DateRange> dateRanges = new HashSet<>();

    @SuppressWarnings("unused")
    private Equipment(){
    }

    public Equipment(YearModelValueObject model, EquipmentType equipmentType,
                     EquipmentMaster master){
        super(DomainObjectId.randomId(EquipmentId.class));
        this.model = model;
        this.equipmentType = equipmentType;
        this.master = master;
        this.dateRanges = new HashSet<>();
    }

    private void defaultDateRanges(){
        if (null == this.dateRanges){
            this.dateRanges = new HashSet<>();
        }
    }

    public Equipment addDateRange(DateRange dateRange){
        defaultDateRanges();
        this.dateRanges.add(dateRange);
        return this;
    }

    public Equipment removeDateRange(DateRange dateRange){
        defaultDateRanges();
        this.dateRanges.remove(dateRange);
        return this;
    }

    public Equipment clearDateRange(){
        defaultDateRanges();
        this.dateRanges.clear();
        return this;
    }

    /**
     * 班次之间的时间计算
     * @return
     */
    public long minutesOfOutWork(){
        return 24*60 - model.getDayType().minutes();
    }

    /**
     * 班次时间计算
     * @return
     */
    public long shiftMinutes(){
        return model.getDayType().minutes();
    }

    /**
     * 计算实际浪费的工作时间
     * @return
     */
    public long actualWasteMinutes(){
        //计算总的时间
        long sumMinutes = 0l;
        for (DateRange range: dateRanges) {
            sumMinutes+=range.minutes();
            TwoTuple<Integer,Long> twoTuple = range.range();
            if (twoTuple.first > 0) {
                sumMinutes -= twoTuple.first * minutesOfOutWork();
            }
        }
        return sumMinutes;
    }

    public EquipmentType getEquipmentType() {
        return equipmentType;
    }

    public YearModelValueObject getModel() {
        return model.clone();
    }
    public EquipmentMaster getMaster() {
        return master.clone();
    }

    public List<DateRange> getDateRanges() {
        defaultDateRanges();
        return new ArrayList<>(dateRanges);
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "equipmentType=" + equipmentType +
                ", model=" + model +
                ", master=" + master +
                ", dateRanges=" + dateRanges +
                '}';
    }
}
