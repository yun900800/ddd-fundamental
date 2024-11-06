package org.ddd.fundamental.material.domain.value;

import org.ddd.fundamental.core.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass
@Embeddable
public class ProcessingProps implements ValueObject,Cloneable {

    private int length;

    private String pressure;

    @SuppressWarnings("unused")
    private ProcessingProps(){}

    private ProcessingProps(int length, String pressure){
        this.length = length;
        this.pressure = pressure;
    }

    public static ProcessingProps create(int length, String pressure) {
        return new ProcessingProps(length,pressure);
    }

    public int getLength() {
        return length;
    }

    public String getPressure() {
        return pressure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProcessingProps)) return false;
        ProcessingProps that = (ProcessingProps) o;
        return length == that.length && Objects.equals(pressure, that.pressure);
    }

    @Override
    public int hashCode() {
        return Objects.hash(length, pressure);
    }

    @Override
    public String toString() {
        return "ProcessingProps{" +
                "length=" + length +
                ", pressure='" + pressure + '\'' +
                '}';
    }

    @Override
    public ProcessingProps clone() {
        try {
            ProcessingProps clone = (ProcessingProps) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
