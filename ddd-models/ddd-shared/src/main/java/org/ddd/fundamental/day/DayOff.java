package org.ddd.fundamental.day;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import org.ddd.fundamental.core.ValueObject;
import org.ddd.fundamental.utils.DateUtils;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
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
    private List<Date> dayOffs;

    public DayOff(List<Date> dayOffs){
        this.dayOffs = dayOffs;
    }

    public static DayOff createDayOff() {
        Date date0 = DateUtils.strToDate("2024-10-01","yyyy-MM-dd");
        Date date1 = DateUtils.strToDate("2024-10-02","yyyy-MM-dd");
        Date date2 = DateUtils.strToDate("2024-10-03","yyyy-MM-dd");
        Date date3 = DateUtils.strToDate("2024-10-04","yyyy-MM-dd");
        return new DayOff(Arrays.asList(date0,date1,date2,date3));
    }

    public List<Date> getDayOffs() {
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
        StringBuilder sb = new StringBuilder();
        for (Date date: dayOffs) {
            String strDate = DateUtils.dateToStr(date,"MM-dd");
            sb.append(strDate).append(",");
        }
        String result = sb.toString();
        return "DayOff{" +
                "假期=" + result.substring(0,result.length()-1) +
                '}';
    }
}
