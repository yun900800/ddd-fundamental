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
public class CalendarTypeValue implements ValueObject, CalculateTime {

    @Type(type = "json")
    @Column(columnDefinition = "json" , name = "shift_list")
    private List<Shift> shiftList;

    private String dayTypeName;

    @SuppressWarnings("unused")
    CalendarTypeValue(){}

    private CalendarTypeValue(List<Shift> shiftList, String dayTypeName){
        this.shiftList = shiftList;
        this.dayTypeName = dayTypeName;
    }

    public static CalendarTypeValue create(String dayTypeName, Shift... shifts){
        return new CalendarTypeValue(Arrays.asList(shifts),dayTypeName);
    }

    public static CalendarTypeValue create(List<Shift> shiftList, String dayTypeName){
        return new CalendarTypeValue(shiftList,dayTypeName);
    }

    public static CalendarTypeValue createTwoShiftDateType(String dayTypeName) {
        return new CalendarTypeValue(Shift.createTwoShift(),dayTypeName);
    }

    public static CalendarTypeValue createThreeShiftDateType(String dayTypeName) {
        return new CalendarTypeValue(Shift.createThreeShift(),dayTypeName);
    }

    public List<Shift> getShiftList() {
        return new ArrayList<>(shiftList);
    }


    public String getDayTypeName() {
        return dayTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CalendarTypeValue)) return false;
        CalendarTypeValue dayType = (CalendarTypeValue) o;
        return Objects.equals(shiftList, dayType.shiftList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shiftList);
    }

    @Override
    public String toString() {
        return objToString();
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
