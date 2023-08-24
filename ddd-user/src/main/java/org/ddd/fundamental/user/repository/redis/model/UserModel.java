package org.ddd.fundamental.user.repository.redis.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("UserModel")
public class UserModel {

    @Id
    private String userId;

    private Integer count;

    private Integer successCount;

    private Integer failedCount;

    public UserModel(){
    }

    public UserModel(String userId,Integer count){
        this.count = count;
        this.userId = userId;
        this.successCount = 0;
        this.failedCount = 0;
    }

    public UserModel successIncrement() {
        this.successCount++;
        return this;
    }

    public UserModel failedIncrement() {
        this.failedCount++;
        return this;
    }

    public UserModel increment() {
        this.count++;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public Integer getCount() {
        return count;
    }

    public Integer getSuccessCount() {
        return successCount;
    }

    public Integer getFailedCount() {
        return failedCount;
    }
}
