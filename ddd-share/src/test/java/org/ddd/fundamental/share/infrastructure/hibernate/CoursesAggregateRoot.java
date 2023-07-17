package org.ddd.fundamental.share.infrastructure.hibernate;

import org.ddd.fundamental.share.domain.AggregateRoot;

public class CoursesAggregateRoot extends AggregateRoot {

    private String id;

    private String name;

    private String duration;

    public CoursesAggregateRoot(String id, String name,String duration) {
        this.id = id;
        this.name = name;
        this.duration =duration;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDuration() {
        return duration;
    }
}
