package org.ddd.fundamental.repository.core.saga;

import org.ddd.fundamental.repository.core.EntityModel;
public abstract class Message extends EntityModel<Long> {

    private String name;

    public Message() {
        super(0L);
        this.name = this.getClass().getName();
    }
    public Message(Long id) {
        super(id);
        this.name = this.getClass().getName();
    }

    public String getName() {
        return name;
    }
}
