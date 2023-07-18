package org.ddd.fundamental.share.infrastructure.persistence.hibernate;


import javax.persistence.*;

@Table
@Entity
public class Courses {

    @Column( columnDefinition = "varchar(100)", nullable = false)
    @EmbeddedId
    private StringIdentifier id;

    private String name;

    private String duration;

    public Courses() {

    }

    public Courses(StringIdentifier id, String name,String duration) {
        this.id = id;
        this.name = name;
        this.duration = duration;
    }

    public StringIdentifier getId() {
        return id;
    }

    public void setId(StringIdentifier id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
