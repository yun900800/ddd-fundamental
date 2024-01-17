package org.ddd.fundamental.core.domain;

import java.time.Instant;

public class OpsLog {
    private final Instant optAt;
    private final String optBy;
    private final String obn;//operatedByName
    private final String note;

    public OpsLog(Instant optAt, String optBy, String obn, String note) {
        this.optAt = optAt;
        this.optBy = optBy;
        this.obn = obn;
        this.note = note;
    }

    public Instant getOperatedAt() {
        return optAt;
    }

    public String getOperatedBy() {
        return optBy;
    }

    public String getOperatedByName() {
        return obn;
    }
}
