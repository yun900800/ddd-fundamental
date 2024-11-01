package org.ddd.fundamental.day;


import io.hypersistence.utils.hibernate.type.json.JsonType;
import org.ddd.fundamental.core.ValueObject;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@MappedSuperclass
@Embeddable
@TypeDef(
        name = "json",
        typeClass = JsonType.class
)
public class DayType implements ValueObject, CalculateTime {

    @Type(type = "json")
    @Column(columnDefinition = "json" , name = "shift_list")
    private List<Shift> shiftList;

    private String dayTypeName;

    @SuppressWarnings("unused")
    DayType(){}

    public DayType(List<Shift> shiftList,String dayTypeName){
        this.shiftList = shiftList;
        this.dayTypeName = dayTypeName;
    }

    public static DayType create(String dayTypeName,Shift... shifts){
        return new DayType(Arrays.asList(shifts),dayTypeName);
    }

    public static DayType createTwoShiftDateType(String dayTypeName) {
        return new DayType(Shift.createTwoShift(),dayTypeName);
    }

    public static DayType createThreeShiftDateType(String dayTypeName) {
        return new DayType(Shift.createThreeShift(),dayTypeName);
    }

    public static DayType create(List<Shift> shiftList,String dayTypeName){
        return new DayType(shiftList,dayTypeName);
    }


    public List<CalculateTime> getShiftList() {
        return new ArrayList<>(shiftList);
    }


    public String getDayTypeName() {
        return dayTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DayType)) return false;
        DayType dayType = (DayType) o;
        return Objects.equals(shiftList, dayType.shiftList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shiftList);
    }

    @Override
    public String toString() {
        return "DayType{" +
                "dayTypeName=" + dayTypeName +
                ", shiftList=" + shiftList  +
                "}";
    }

    @Override
    public long minutes() {
        if (shiftList == null || shiftList.size() ==0){
            return 0;
        }
        long sum = 0 ;
        for (CalculateTime time: shiftList) {
            sum+= time.minutes();
        }
        return sum;
    }
}
