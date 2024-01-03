package org.ddd.fundamental.core.domain.entity;

import org.ddd.fundamental.core.domain.DomainModel;
import org.ddd.fundamental.core.domain.Versionable;
import org.ddd.fundamental.core.enums.Status;
import org.ddd.fundamental.core.exception.InvalidOperationException;
import org.ddd.fundamental.core.validation.impl.RuleManager;

import java.time.LocalDateTime;

public abstract class EntityModel<TID extends Comparable<TID>> extends DomainModel implements Versionable {
    private Identity<? extends Comparable> id;
    //版本信息，用于控制并发
    private int version;
    //创建日期
    private LocalDateTime createdDate = LocalDateTime.now();
    //变更日期
    private LocalDateTime updatedDate = LocalDateTime.now();
    //状态
    private  Status status =  Status.ACTIVE;

    protected EntityModel(Identity<? extends Comparable> id) {
        this(id, null, null);
    }

    protected EntityModel(Identity<? extends Comparable> id, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this(id,  Status.ACTIVE, 0, createdDate, updatedDate);
    }

    protected EntityModel(Identity<? extends Comparable> id,  Status status, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this(id, status, 0, createdDate, updatedDate);
    }

    protected EntityModel(Identity<? extends Comparable> id,  Status status, int version, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.version = version;
        this.initializeForNewCreation();
        if (status != null && status !=  Status.UNKNOWN) {
            this.status = status;
        }
        if (createdDate != null) {
            this.createdDate = createdDate;
        }
        if (updatedDate != null) {
            this.updatedDate = updatedDate;
        }
    }

    /**
     * 当前对象置无效
     */
    public void disable() throws InvalidOperationException {
        this.status =  Status.INACTIVE;
        this.updatedDate = LocalDateTime.now();
    }

    @Override
    protected void addRule(RuleManager ruleManager) {
        super.addRule(ruleManager);
    }

    @Override
    public boolean equals(Object object){
        if(object == null || !(object instanceof EntityModel)){
            return false;
        }
        return this.getId().equals(((EntityModel) object).getId());
    }

    @Override
    public int hashCode(){
        return this.id.hashCode();
    }

    public Identity getId() {
        return id;
    }

    /**
     * 获取版本信息。
     * @return 版本信息
     */
    @Override
    public int getVersion() {
        return this.version;
    }
}
