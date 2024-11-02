package org.ddd.fundamental.workorder.domain.model;

import org.ddd.fundamental.core.AbstractAggregateRoot;
import org.ddd.fundamental.day.YearModelValueObject;
import org.ddd.fundamental.workorder.domain.value.YearModelId;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "base_year_model")
public class YearModel extends AbstractAggregateRoot<YearModelId> {
    private YearModelValueObject yearModel;

    private YearModel(){

    }

    public YearModel(YearModelValueObject yearModel){
        super(YearModelId.randomId(YearModelId.class));
        this.yearModel = yearModel;
    }

    public YearModelValueObject getYearModel() {
        return yearModel;
    }

    @Override
    public String toString() {
        return "YearModel{" +
                "yearModel=" + yearModel +
                '}';
    }

    @Override
    public long created() {
        return 0;
    }
}
