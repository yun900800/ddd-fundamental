package org.ddd.fundamental.day;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.core.ValueObject;
import org.ddd.fundamental.jackson.LocalDateDeserializer;
import org.ddd.fundamental.jackson.LocalDateSerializer;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@MappedSuperclass
@Embeddable
@TypeDef(
        name = "json",
        typeClass = JsonType.class
)
@Slf4j
public class DayOff implements ValueObject {
    @SuppressWarnings("unused")
    DayOff(){}

    @Type(type = "json")
    @Column(columnDefinition = "json" , name = "day_offs")
    private List<DateOffWrapper> dayOffs;

    private int year;

    private DayOff(List<DateOffWrapper> dayOffs, int year){
        this.dayOffs = dayOffs;
        this.year = year;
    }

    public static DayOff create(List<DateOffWrapper> dayOffs, int year){
        return new DayOff(dayOffs,year);
    }

    public static Set<LocalDate> getHolidays(int year) {
        Set<LocalDate> holidays = new HashSet<>();
        holidays.add(LocalDate.of(year,1,1));
        holidays.add(LocalDate.of(year,5,1));
        holidays.add(LocalDate.of(year,10,1));
        return holidays;
    }

    public static List<LocalDate> getWorkingDays(int year){
        List<LocalDate> workDays = new ArrayList<>();
        LocalDate start = LocalDate.of(year,1,1);
        LocalDate end = LocalDate.of(year,12,31);
        Set<LocalDate> holidays = getHolidays(year);
        LocalDate currentDate = start;
        while(currentDate.isBefore(end) || currentDate.equals(end)) {
            if (currentDate.getDayOfWeek() != DayOfWeek.SATURDAY &&
                    currentDate.getDayOfWeek() != DayOfWeek.SUNDAY &&
                    !holidays.contains(currentDate)) {
                workDays.add(currentDate);
            }
            currentDate = currentDate.plusDays(1);
        }
        return workDays;
    }

    public int getYear() {
        return year;
    }

    public static Set<LocalDate> getWeekends(int year){
        Set<LocalDate> weekends = new HashSet<>();
        LocalDate start = LocalDate.of(year,1,1);
        LocalDate end = LocalDate.of(year,12,31);
        LocalDate currentDate = start;
        while(currentDate.isBefore(end) || currentDate.equals(end)) {
            if (currentDate.getDayOfWeek() == DayOfWeek.SATURDAY ||
                    currentDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
                weekends.add(currentDate);
            }
            currentDate = currentDate.plusDays(1);
        }
        return weekends;
    }

    /**
     * 计算一年休息的日期
     * @param year
     * @return
     */
    public static List<LocalDate> getRestDays(int year){
        Set<LocalDate> holidays = getHolidays(year);
        Set<LocalDate> weekends = getWeekends(year);
        holidays.addAll(weekends);
        return new ArrayList<>(holidays);
    }



   public static DayOff createDayOff(int year) {
        List<LocalDate> restDays = getRestDays(year);
        List<DateOffWrapper> dateOffWrappers = restDays.stream().map(v->DateOffWrapper.valueOf(v)).collect(Collectors.toList());
        return new DayOff(dateOffWrappers,year);
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
        return year == dayOff.year && Objects.equals(dayOffs, dayOff.dayOffs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dayOffs, year);
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
