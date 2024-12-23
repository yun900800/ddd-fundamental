package org.ddd.fundamental.day;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import org.ddd.fundamental.core.ValueObject;
import org.ddd.fundamental.jackson.LocalDateDeserializer;
import org.ddd.fundamental.jackson.LocalDateSerializer;
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
    private List<DateOffWrapper> dayOffs;

    public DayOff(List<DateOffWrapper> dayOffs){
        this.dayOffs = dayOffs;
    }

    public static DayOff createDayOff() {
        DateOffWrapper date0 = DateOffWrapper.valueOf(LocalDate.parse("2024-10-01")) ;
        DateOffWrapper date1 = DateOffWrapper.valueOf(LocalDate.parse("2024-10-02"));
        DateOffWrapper date2 = DateOffWrapper.valueOf(LocalDate.parse("2024-10-03"));
        DateOffWrapper date3 = DateOffWrapper.valueOf(LocalDate.parse("2024-10-04"));
        return new DayOff(Arrays.asList(date0,date1,date2,date3));
    }

    public List<DateOffWrapper> getDayOffs() {
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
        for (DateOffWrapper date: dayOffs) {
            String strDate = date.getOffDate().format(formatter);
            sb.append(strDate).append(",");
        }
        String result = sb.toString();
        return "DayOff{" +
                "假期=" + result.substring(0,result.length()-1) +
                '}';
    }
}
