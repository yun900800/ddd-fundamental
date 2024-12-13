package org.ddd.fundamental.day;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import org.ddd.fundamental.core.ValueObject;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@MappedSuperclass
@Embeddable
@TypeDef(
        name = "json",
        typeClass = JsonType.class
)
public class DayOff implements ValueObject {
    @SuppressWarnings("unused")
    DayOff(){}

    @Type(type = "json")
    @Column(columnDefinition = "json" , name = "day_offs")
    private List<LocalDate> dayOffs;

    public DayOff(List<LocalDate> dayOffs){
        this.dayOffs = dayOffs;
    }

    public static DayOff createDayOff() {
        LocalDate date0 = LocalDate.parse("2024-10-01");
        LocalDate date1 = LocalDate.parse("2024-10-02");
        LocalDate date2 = LocalDate.parse("2024-10-03");
        LocalDate date3 = LocalDate.parse("2024-10-04");
        return new DayOff(Arrays.asList(date0,date1,date2,date3));
    }

    public List<LocalDate> getDayOffs() {
        return new ArrayList<>(dayOffs);
    }

    public int getDays(){
        return this.dayOffs.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DayOff)) return false;
        DayOff dayOff = (DayOff) o;
        return Objects.equals(dayOffs, dayOff.dayOffs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dayOffs);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
        StringBuilder sb = new StringBuilder();
        for (LocalDate date: dayOffs) {
            String strDate = date.format(formatter);
            sb.append(strDate).append(",");
        }
        String result = sb.toString();
        return "DayOff{" +
                "假期=" + result.substring(0,result.length()-1) +
                '}';
    }
}
