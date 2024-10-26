package org.ddd.fundamental.date;


import io.hypersistence.utils.hibernate.type.json.JsonType;
import org.ddd.fundamental.core.ValueObject;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@MappedSuperclass
@Embeddable
@TypeDef(
        name = "json",
        typeClass = JsonType.class
)
public class DayType implements ValueObject {

    @Type(type = "json")
    @Column(columnDefinition = "json" , name = "shift_list")
    private List<Shift> shiftList;

    private int shiftCount;

    private String dayTypeName;

    @SuppressWarnings("unused")
    DayType(){}

    public DayType(List<Shift> shiftList,String dayTypeName){
        this.shiftList = shiftList;
        this.shiftCount = shiftList.size();
        this.dayTypeName = dayTypeName;
    }

    public List<Shift> getShiftList() {
        return new ArrayList<>(shiftList);
    }

    public int getShiftCount() {
        return shiftCount;
    }

    public String getDayTypeName() {
        return dayTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DayType)) return false;
        DayType dayType = (DayType) o;
        return shiftCount == dayType.shiftCount && Objects.equals(shiftList, dayType.shiftList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shiftList, shiftCount);
    }
}
