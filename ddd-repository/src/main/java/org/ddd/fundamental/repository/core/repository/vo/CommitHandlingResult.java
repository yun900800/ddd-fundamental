package org.ddd.fundamental.repository.core.repository.vo;

public class CommitHandlingResult {

    private boolean commitFlag;

    private String message;

    public CommitHandlingResult(){

    }

    public CommitHandlingResult(boolean commitFlag, String message) {
        this.commitFlag = commitFlag;
        this.message = message;
    }

    public boolean isCommitFlag() {
        return commitFlag;
    }

    public String getMessage() {
        return message;
    }
}
