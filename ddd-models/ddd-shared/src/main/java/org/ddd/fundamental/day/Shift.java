package org.ddd.fundamental.day;

import org.ddd.fundamental.core.ValueObject;
import org.ddd.fundamental.utils.DateUtils;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 班次
 * 用于构建日历对象
 */
@MappedSuperclass
@Embeddable
public class Shift implements ValueObject , CalculateTime{
    @Temporal(TemporalType.TIMESTAMP)
    private LocalTime start;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalTime end;

    private String shiftName;

    @SuppressWarnings("unused")
    Shift(){
    }

    public Shift(LocalTime start, LocalTime end,String shiftName){
        this.shiftName = shiftName;
        this.start = start;
        this.end = end;
    }

    public static List<CalculateTime> createTwoShift() {
        LocalTime start = LocalTime.parse("08:00:00");

        LocalTime end = LocalTime.parse("14:00:00");
        Shift shift = new Shift(start,end,"第一班次早班");

        LocalTime start1 = LocalTime.parse("14:00:00");
        LocalTime end1 = LocalTime.parse("20:00:00");
        Shift shift1 = new Shift(start1,end1,"第二班次晚班");
        return Arrays.asList(shift,shift1);
    }

    public static List<CalculateTime> createThreeShift() {
        LocalTime start = LocalTime.parse("06:00:00");
        LocalTime end = LocalTime.parse("12:00:00");
        Shift shift = new Shift(start,end,"第一班次早班");

        LocalTime start1 = LocalTime.parse("12:00:00");
        LocalTime end1 = LocalTime.parse("18:00:00");
        Shift shift1 = new Shift(start1,end1,"第二班次中班");

        LocalTime start2 = LocalTime.parse("18:00:00");
        LocalTime end2 = LocalTime.parse("23:59:59");
        Shift shift2 = new Shift(start2,end2,"第三班次晚班");
        return Arrays.asList(shift,shift1,shift2);
    }

    public LocalTime getStart() {
        return start;
    }

    public String formatStart() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return start.format(formatter);
    }

    public LocalTime getEnd() {
        return end;
    }

    public String formatEnd(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return end.format(formatter);
    }

    public String getShiftName() {
        return shiftName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shift)) return false;
        Shift shift = (Shift) o;
        return Objects.equals(formatStart(), shift.formatStart()) && Objects.equals(formatEnd(), shift.formatEnd()) && Objects.equals(shiftName, shift.shiftName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(formatStart(), formatEnd(), shiftName);
    }

    @Override
    public String toString() {
        return "Shift{" +
                shiftName + " = " + formatStart() +
                " - " + formatEnd() +
                "}";
    }

    @Override
    public long minutes() {
        Duration duration = Duration.between(start,end);
        return duration.toMinutes();
    }

}
