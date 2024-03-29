package org.ddd.fundamental.core.domain;

import org.ddd.fundamental.core.domain.event.DomainEvent;
import org.ddd.fundamental.core.domain.user.User;
import org.ddd.fundamental.core.utils.Identified;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static java.time.Instant.now;
import static java.util.Objects.requireNonNull;
import static org.ddd.fundamental.core.utils.CommonUtils.requireNonBlank;

public abstract class AggregateRoot implements Identified {
    private static final int MAX_OPS_LOG_SIZE = 20;

    private String id;//通过Snowflake算法生成
    private String tenantId;//在多租户下，所有聚合根都需要一个tenantId来对应某个租户
    private Instant createdAt;//创建时间
    private String createdBy;//创建人的MemberId
    private String creator;//创建人姓名
    private Instant updatedAt;//更新时间
    private String updatedBy;//更新人MemberId
    private String updater;//更新人姓名
    private List<DomainEvent> events;//领域事件列表，用于临时存放完成某个业务流程中所发出的事件，会被BaseRepository保存到事件表中
    private LinkedList<OpsLog> opsLogs;//操作日志

    private Long _version;//版本号，实现乐观锁

    protected AggregateRoot() {
        this.clearEvents();
    }

    protected AggregateRoot(String id, User user) {
        requireNonBlank(id, "ID must not be blank.");
        requireNonNull(user, "User must not be null.");
        requireNonBlank(user.getTenantId(), "Tenant ID must not be blank.");

        this.id = id;
        this.tenantId = user.getTenantId();
        this.createdAt = now();
        this.createdBy = user.getMemberId();
        this.creator = user.getName();
    }

    protected AggregateRoot(String id, String tenantId, User user) {
        requireNonBlank(id, "AR ID must not be blank.");
        requireNonBlank(tenantId, "Tenant ID must not be blank.");
        requireNonNull(user, "User must not be null.");

        this.id = id;
        this.tenantId = tenantId;
        this.createdAt = now();
        this.createdBy = user.getMemberId();
        this.creator = user.getName();
    }

    protected void addOpsLog(String note, User user) {
        if (user.isLoggedIn()) {
            //OpsLog log = OpsLog.builder().note(note).optAt(now()).optBy(user.getMemberId()).obn(user.getName()).build();
            List<OpsLog> opsLogs = allOpsLogs();

            //opsLogs.add(log);
            if (opsLogs.size() > MAX_OPS_LOG_SIZE) {//最多保留最近100条
                this.opsLogs.remove();
            }

            this.updatedAt = now();
            this.updatedBy = user.getMemberId();
            this.updater = user.getName();
        }
    }

    private List<OpsLog> allOpsLogs() {
        if (opsLogs == null) {
            this.opsLogs = new LinkedList<>();
        }

        return opsLogs;
    }

    protected void raiseEvent(DomainEvent event) {
        event.setArInfo(this);
        allEvents().add(event);
    }

    public void clearEvents() {
        this.events = null;
    }

    private List<DomainEvent> allEvents() {
        if (events == null) {
            this.events = new ArrayList<>();
        }

        return events;
    }

    @Override
    public String getIdentifier() {
        return id;
    }

    public String getId() {
        return id;
    }

    public String getTenantId() {
        return tenantId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getCreator() {
        return creator;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public String getUpdater() {
        return updater;
    }

    public List<DomainEvent> getEvents() {
        return events;
    }

    public LinkedList<OpsLog> getOpsLogs() {
        return opsLogs;
    }

    public Long get_version() {
        return _version;
    }
}
