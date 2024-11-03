package org.ddd.fundamental.workorder.domain.model;

import org.ddd.fundamental.core.AbstractAggregateRoot;
import org.ddd.fundamental.day.YearModelValue;
import org.ddd.fundamental.workorder.domain.value.YearModelId;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "base_year_model")
public class YearModel extends AbstractAggregateRoot<YearModelId> {
    private YearModelValue yearModel;

    private YearModel(){

    }

    public YearModel(YearModelValue yearModel){
        super(YearModelId.randomId(YearModelId.class));
        this.yearModel = yearModel;
    }

    public YearModelValue getYearModel() {
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
