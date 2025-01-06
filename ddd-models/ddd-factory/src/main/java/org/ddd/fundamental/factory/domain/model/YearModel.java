package org.ddd.fundamental.factory.domain.model;

import org.ddd.fundamental.core.AbstractAggregateRoot;
import org.ddd.fundamental.day.YearModelId;
import org.ddd.fundamental.day.YearModelValue;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "year_model")
public class YearModel extends AbstractAggregateRoot<YearModelId> {

    private YearModelValue yearModel;

    @SuppressWarnings("unused")
    protected YearModel(){}

    private YearModel(YearModelValue yearModel){
        super(YearModelId.randomId(YearModelId.class));
        this.yearModel = yearModel;
    }

    public static YearModel create(YearModelValue yearModel){
        return new YearModel(yearModel);
    }

    public YearModelValue getYearModel() {
        return yearModel.clone();
    }

    @Override
    public long created() {
        return 0;
    }
}
