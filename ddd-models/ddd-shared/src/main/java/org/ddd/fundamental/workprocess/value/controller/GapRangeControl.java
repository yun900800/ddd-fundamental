package org.ddd.fundamental.workprocess.value.controller;

import org.ddd.fundamental.core.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

/**
 * 工序前后间隔时间控制
 */
@MappedSuperclass
@Embeddable
public class GapRangeControl implements ValueObject,Cloneable {

    private boolean isPreGapRange;

    private boolean isNextGapRange;

    @SuppressWarnings("unused")
    private GapRangeControl(){}

    private GapRangeControl(boolean isPreGapRange,
                            boolean isNextGapRange){
        this.isPreGapRange = isPreGapRange;
        this.isNextGapRange = isNextGapRange;
    }

    public static GapRangeControl create(boolean isPreGapRange,
                                         boolean isNextGapRange){
        return new GapRangeControl(isPreGapRange,isNextGapRange);
    }

    public boolean isPreGapRange() {
        return isPreGapRange;
    }

    public boolean isNextGapRange() {
        return isNextGapRange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GapRangeControl)) return false;
        GapRangeControl that = (GapRangeControl) o;
        return isPreGapRange == that.isPreGapRange && isNextGapRange == that.isNextGapRange;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isPreGapRange, isNextGapRange);
    }

    @Override
    public String toString() {
        return "GapRangeControl{" +
                "isPreGapRange=" + isPreGapRange +
                ", isNextGapRange=" + isNextGapRange +
                '}';
    }

    @Override
    public GapRangeControl clone() {
        try {
            GapRangeControl clone = (GapRangeControl) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
