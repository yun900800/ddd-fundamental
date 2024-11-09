package org.ddd.fundamental.workprocess.value;

import org.ddd.fundamental.core.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

/**
 * 工序节拍
 */
@Embeddable
@MappedSuperclass
public class WorkProcessBeat implements ValueObject,Cloneable {

    @SuppressWarnings("unused")
    private WorkProcessBeat(){}

    /**
     * 数量
     */
    private int count;

    /**
     * 分钟数
     */
    private int minutes;

    private WorkProcessBeat(int count,int minutes){
        this.count = count;
        this.minutes = minutes;
    }

    public static WorkProcessBeat create(int count,int minutes){
        return new WorkProcessBeat(count,minutes);
    }

    public int getCount() {
        return count;
    }

    public int getMinutes() {
        return minutes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkProcessBeat)) return false;
        WorkProcessBeat that = (WorkProcessBeat) o;
        return count == that.count && minutes == that.minutes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(count, minutes);
    }

    @Override
    public String toString() {
        return "WorkProcessBeat{" +
                "count=" + count +
                ", minutes=" + minutes +
                '}';
    }

    @Override
    public WorkProcessBeat clone() {
        try {
            WorkProcessBeat clone = (WorkProcessBeat) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
