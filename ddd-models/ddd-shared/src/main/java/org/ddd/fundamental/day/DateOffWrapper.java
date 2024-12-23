package org.ddd.fundamental.day;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.ddd.fundamental.jackson.LocalDateDeserializer;
import org.ddd.fundamental.jackson.LocalDateSerializer;

import java.time.LocalDate;

public class DateOffWrapper {

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate offDate;

    public DateOffWrapper(){
    }

    public static DateOffWrapper valueOf(LocalDate offDate){
        return new DateOffWrapper(offDate);
    }

    private DateOffWrapper(LocalDate offDate){
        this.offDate = offDate;
    }

    public LocalDate getOffDate() {
        return offDate;
    }
}
