package org.ddd.fundamental.day;

import org.ddd.fundamental.core.ValueObject;
import org.ddd.fundamental.utils.DateUtils;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.text.SimpleDateFormat;
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
public class Shift implements ValueObject {
    @Temporal(TemporalType.TIMESTAMP)
    private Date start;

    @Temporal(TemporalType.TIMESTAMP)
    private Date end;

    private String shiftName;

    @SuppressWarnings("unused")
    Shift(){
    }

    public Shift(Date start, Date end,String shiftName){
        this.shiftName = shiftName;
        this.start = start;
        this.end = end;
    }

    public static List<Shift> createTwoShift() {
        Date start = DateUtils.strToDate("2024-10-27 08:00:00","yyyy-MM-dd HH:mm:ss");
        Date end = DateUtils.strToDate("2024-10-27 14:00:00","yyyy-MM-dd HH:mm:ss");
        Shift shift = new Shift(start,end,"第一班次早班");

        Date start1 = DateUtils.strToDate("2024-10-27 14:00:00","yyyy-MM-dd HH:mm:ss");
        Date end1 = DateUtils.strToDate("2024-10-27 20:00:00","yyyy-MM-dd HH:mm:ss");
        Shift shift1 = new Shift(start1,end1,"第二班次晚班");
        return Arrays.asList(shift,shift1);
    }

    public static List<Shift> createThreeShift() {
        Date start = DateUtils.strToDate("2024-10-27 06:00:00","yyyy-MM-dd HH:mm:ss");
        Date end = DateUtils.strToDate("2024-10-27 12:00:00","yyyy-MM-dd HH:mm:ss");
        Shift shift = new Shift(start,end,"第一班次早班");

        Date start1 = DateUtils.strToDate("2024-10-27 12:00:00","yyyy-MM-dd HH:mm:ss");
        Date end1 = DateUtils.strToDate("2024-10-27 18:00:00","yyyy-MM-dd HH:mm:ss");
        Shift shift1 = new Shift(start1,end1,"第二班次中班");

        Date start2 = DateUtils.strToDate("2024-10-27 18:00:00","yyyy-MM-dd HH:mm:ss");
        Date end2 = DateUtils.strToDate("2024-10-27 24:00:00","yyyy-MM-dd HH:mm:ss");
        Shift shift2 = new Shift(start2,end2,"第三班次晚班");
        return Arrays.asList(shift,shift1,shift2);
    }

    public Date getStart() {
        return start;
    }

    public String formatStart() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return simpleDateFormat.format(start);
    }

    public Date getEnd() {
        return end;
    }

    public String formatEnd(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return simpleDateFormat.format(end);
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
}
