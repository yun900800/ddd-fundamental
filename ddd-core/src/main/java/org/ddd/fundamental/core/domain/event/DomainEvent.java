package org.ddd.fundamental.core.domain.event;

import org.ddd.fundamental.core.domain.AggregateRoot;
import org.ddd.fundamental.core.domain.user.User;

import java.time.Instant;
import java.util.UUID;

import static java.time.Instant.now;
import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public abstract class DomainEvent {
    private String id;//事件ID，不能为空
    private String arTenantId;//事件对应的租户ID，不能为空
    private String arId;//事件对应的聚合根ID，不能为空
    private DomainEventType type;//事件类型
    private DomainEventStatus status;//状态
    private int publishedCount;//已经发布的次数，无论成功与否
    private int consumedCount;//已经被消费的次数，无论成功与否
    private String raisedBy;//引发该事件的memberId
    private Instant raisedAt;//事件产生时间

    protected DomainEvent(DomainEventType type, User user) {
        requireNonNull(type, "Domain event type must not be null.");
        requireNonNull(user, "User must not be null.");

        this.id = newEventId();
        this.type = type;
        this.status = DomainEventStatus.CREATED;
        this.publishedCount = 0;
        this.consumedCount = 0;
        this.raisedBy = user.getMemberId();
        this.raisedAt = now();
    }

    public String newEventId() {
        return "EVT" + UUID.randomUUID();
    }

    public void setArInfo(AggregateRoot ar) {
        this.arTenantId = ar.getTenantId();
        this.arId = ar.getId();
    }

    public boolean isConsumedBefore() {
        return this.consumedCount > 0;
    }

    public boolean isNotConsumedBefore() {
        return !isConsumedBefore();
    }

    public boolean isRaisedByHuman() {
        return isNotBlank(raisedBy);
    }

    public String getId() {
        return id;
    }

    public String getArTenantId() {
        return arTenantId;
    }

    public String getArId() {
        return arId;
    }

    public DomainEventType getType() {
        return type;
    }

    public DomainEventStatus getStatus() {
        return status;
    }

    public int getPublishedCount() {
        return publishedCount;
    }

    public int getConsumedCount() {
        return consumedCount;
    }

    public String getRaisedBy() {
        return raisedBy;
    }

    public Instant getRaisedAt() {
        return raisedAt;
    }
}
