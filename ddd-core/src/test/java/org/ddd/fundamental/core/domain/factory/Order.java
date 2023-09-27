package org.ddd.fundamental.core.domain.factory;

import org.ddd.fundamental.core.domain.entity.EntityModel;
import org.ddd.fundamental.core.domain.entity.Identity;
import org.ddd.fundamental.core.enums.Status;

import java.time.LocalDateTime;

public class Order extends EntityModel<Long> {
    protected Order(Identity<? extends Comparable> id) {
        super(id);
    }

    protected Order(Identity<? extends Comparable> id, LocalDateTime createdDate, LocalDateTime updatedDate) {
        super(id, createdDate, updatedDate);
    }

    protected Order(Identity<? extends Comparable> id, Status status, LocalDateTime createdDate, LocalDateTime updatedDate) {
        super(id, status, createdDate, updatedDate);
    }

    protected Order(Identity<? extends Comparable> id, Status status, int version, LocalDateTime createdDate, LocalDateTime updatedDate) {
        super(id, status, version, createdDate, updatedDate);
    }
}
