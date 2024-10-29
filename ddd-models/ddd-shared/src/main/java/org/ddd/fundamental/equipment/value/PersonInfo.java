package org.ddd.fundamental.equipment.value;

import org.ddd.fundamental.core.ValueObject;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.Objects;


@Embeddable
@MappedSuperclass
public class PersonInfo implements ValueObject, Cloneable {

    /**
     * 设备需要的人数
     */
    private int personCount;

    /**
     * 人员的资质信息
     */
    private String qualification;

    @SuppressWarnings("unused")
    private PersonInfo(){}

    private PersonInfo(int personCount,String qualification){
        this.personCount = personCount;
        this.qualification = qualification;
    }

    public static PersonInfo create(int personCount,String qualification) {
        return new PersonInfo(personCount,qualification);
    }

    public int getPersonCount() {
        return personCount;
    }

    public String getQualification() {
        return qualification;
    }

    @Override
    public String toString() {
        return "PersonInfo{" +
                "personCount=" + personCount +
                ", qualification='" + qualification + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonInfo)) return false;
        PersonInfo that = (PersonInfo) o;
        return personCount == that.personCount && Objects.equals(qualification, that.qualification);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personCount, qualification);
    }

    @Override
    public PersonInfo clone() {
        try {
            PersonInfo clone = (PersonInfo) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
