package org.ddd.fundamental.day;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import org.ddd.fundamental.core.ValueObject;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
}
