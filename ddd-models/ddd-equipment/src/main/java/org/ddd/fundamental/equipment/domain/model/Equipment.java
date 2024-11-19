package org.ddd.fundamental.equipment.domain.model;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.core.AbstractAggregateRoot;
import org.ddd.fundamental.core.DomainObjectId;
import org.ddd.fundamental.day.YearModelValue;
import org.ddd.fundamental.day.range.DateRange;
import org.ddd.fundamental.equipment.value.EquipmentMaster;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.factory.ToolingEquipmentId;
import org.ddd.fundamental.tuple.TwoTuple;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "equipment")
@Slf4j
public class Equipment extends AbstractAggregateRoot<EquipmentId> {

    private EquipmentType equipmentType;

    @Embedded
    private YearModelValue model;

    @Embedded
    private EquipmentMaster master;

    @Type(type = "json")
    @Column(columnDefinition = "json", name = "tooling_equipment_ids")
    private Set<EquipmentId> toolingEquipmentIds = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name="equipment_dates",
            joinColumns=@JoinColumn(name="equipment_id")
    )
    //@OrderColumn(name = "index_id")
    // 注意这里的集合如果使用ArrayList
//    create table equipment_dates (
//            equipment_id varchar(255) not null,
//    description varchar(255) not null,
//    end datetime not null,
//    start datetime not null
//            )
    //如果修改成HashSet
//    create table equipment_dates (
//            equipment_id varchar(255) not null,
//    description varchar(255) not null,
//    end datetime not null,
//    start datetime not null,
//    primary key (equipment_id, description, end, start)
//    )
    //如果是用List需要配合@OrderColumn(name = "index_id")注解 https://vladmihalcea.com/how-to-optimize-unidirectional-collections-with-jpa-and-hibernate/
    //否则使用HashSet
    private Set<DateRange> dateRanges = new HashSet<>();

    @SuppressWarnings("unused")
    private Equipment(){
    }

    public Equipment(YearModelValue model, EquipmentType equipmentType,
                     EquipmentMaster master){
        super(DomainObjectId.randomId(EquipmentId.class));
        this.model = model;
        this.equipmentType = equipmentType;
        this.master = master;
        this.dateRanges = new HashSet<>();
    }

    private void defaultDateRanges(){
        if (null == this.dateRanges){
            log.info("重构集合对象");
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

    private void defaultToolingIds(){
        if (null == this.toolingEquipmentIds){
            this.toolingEquipmentIds = new HashSet<>();
        }
    }

    public Equipment addToolingId(EquipmentId id){
        defaultToolingIds();
        this.toolingEquipmentIds.add(id);
        return this;
    }

    public Equipment removeToolingId(ToolingEquipmentId id){
        defaultToolingIds();
        this.toolingEquipmentIds.remove(id);
        return this;
    }

    public Equipment clearToolingIds(){
        defaultToolingIds();
        this.toolingEquipmentIds.clear();
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

    public YearModelValue getModel() {
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

    @Override
    public long created() {
        return 0;
    }
}
