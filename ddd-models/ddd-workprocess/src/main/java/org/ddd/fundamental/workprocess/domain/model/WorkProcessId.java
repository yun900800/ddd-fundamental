package org.ddd.fundamental.workprocess.domain.model;

import org.ddd.fundamental.core.DomainObjectId;

/**
 * 工序id
 */
public class WorkProcessId extends DomainObjectId {
    public WorkProcessId(String uuid) {
        super(uuid);
    }
}
