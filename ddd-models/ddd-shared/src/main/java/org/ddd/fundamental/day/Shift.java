package org.ddd.fundamental.day;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.ddd.fundamental.core.ValueObject;
import org.ddd.fundamental.jackson.LocalTimeDeserializer;
import org.ddd.fundamental.jackson.LocalTimeSerializer;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 班次
 * 用于构建日历对象
 */
@MappedSuperclass
@Embeddable
public class Shift implements ValueObject , CalculateTime{

    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime start;

    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime end;

    private String shiftName;

    @Transient
    @JsonIgnore
    private  static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    @SuppressWarnings("unused")
    public Shift(){
    }

    private Shift(LocalTime start, LocalTime end,String shiftName){
        this.shiftName = shiftName;
        this.start = start;
        this.end = end;
    }

    public static Shift create(LocalTime start, LocalTime end,String shiftName){
        return new Shift(start,end,shiftName);
    }

    public static Shift createFromStart(LocalTime start, int hour, String shiftName){
        LocalTime endTime = start.plusHours(hour);
        return create(start,endTime,shiftName);
    }

    public static Shift createFromEnd(LocalTime end, int hour, String shiftName){
        LocalTime startTime = end.plusHours(-hour);
        return create(startTime,end,shiftName);
    }

    public static List<Shift> createRandomShiftList(LocalTime start,LocalTime end, int maxMinutes,int minMinutes){
        List<Shift> result = new ArrayList<>();
        LocalTime currentDate = start;
        Random random = new Random();
        LocalTime endTime = start;
        int startHour = start.getHour();
        int endHour = end.getHour();
        int currentStartHour = currentDate.getHour();
        int currentEndHour = endTime.getHour();
        int index = 0;
        while (currentStartHour >=startHour && currentEndHour>=startHour
                && currentStartHour < endHour && currentEndHour< endHour){
            int randomMinutes = random.nextInt(maxMinutes - minMinutes + 1) + minMinutes;
            endTime = currentDate.plusMinutes(randomMinutes);
            Shift shift = create(currentDate,endTime,"第"+ (index+1)+"班次");
            result.add(shift);
            currentDate = endTime.plusMinutes(10);
            index++;
            currentStartHour = currentDate.getHour();
            currentEndHour = endTime.getHour();
        }
        result.remove(result.get(result.size()-1));
        return result;
    }


    public static List<Shift> createTwoShift() {
        LocalTime start = LocalTime.parse("08:00:00");

        LocalTime end = LocalTime.parse("14:00:00");
        Shift shift = new Shift(start,end,"第一班次早班");

        LocalTime start1 = LocalTime.parse("14:00:00");
        LocalTime end1 = LocalTime.parse("20:00:00");
        Shift shift1 = new Shift(start1,end1,"第二班次晚班");
        return Arrays.asList(shift,shift1);
    }

    public static List<Shift> createThreeShift() {
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
        return start.format(formatter);
    }

    public LocalTime getEnd() {
        return end;
    }

    public String formatEnd(){
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
        return Objects.equals(formatStart(), shift.formatStart()) &&
                Objects.equals(formatEnd(), shift.formatEnd()) &&
                Objects.equals(shiftName, shift.shiftName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(formatStart(), formatEnd(), shiftName);
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long minutes() {
        Duration duration = Duration.between(start,end);
        return duration.toMinutes();
    }

    public double hours(){
        Duration duration = Duration.between(start,end);
        return duration.toHours();
    }

}
