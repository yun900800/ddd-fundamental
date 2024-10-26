package org.ddd.fundamental.date;

import org.ddd.fundamental.core.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.Date;
import java.util.Objects;

/**
 * 班次
 */
@MappedSuperclass
@Embeddable
public class Shift implements ValueObject {
    private Date start;

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

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public String getShiftName() {
        return shiftName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shift)) return false;
        Shift shift = (Shift) o;
        return Objects.equals(start, shift.start) && Objects.equals(end, shift.end) && Objects.equals(shiftName, shift.shiftName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end, shiftName);
    }

    @Override
    public String toString() {
        return "Shift{" +
                "start=" + start +
                ", end=" + end +
                ", shiftName='" + shiftName + '\'' +
                '}';
    }
}
