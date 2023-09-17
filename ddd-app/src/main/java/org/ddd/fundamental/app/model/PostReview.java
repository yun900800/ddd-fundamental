package org.ddd.fundamental.app.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "post_review")
public class PostReview implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "review_content")
    private String reviewContent;

    @Column(name = "create_time")
    private Long createTime;

    @Column(name = "update_time")
    private Long updateTime;

    public PostReview(){

    }
    private PostReview(Long userId,Long postId,String reviewContent) {
        this.userId = userId;
        this.postId = postId;
        this.reviewContent = reviewContent;
        long currentTime = System.currentTimeMillis();
        this.createTime =currentTime;
        this.updateTime =currentTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public static PostReview newPostReview(Long userId, Long postId, String reviewContent) {
        return new PostReview(userId, postId, reviewContent);
    }

    public PostReview modifyReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
        long currentTime = System.currentTimeMillis();
        this.updateTime =currentTime;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostReview that = (PostReview) o;
        return id.equals(that.id) && userId.equals(that.userId) && postId.equals(that.postId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, postId);
    }

    @Override
    public String toString() {
        return "PostReview{" +
                "id=" + id +
                ", userId=" + userId +
                ", postId=" + postId +
                ", reviewContent='" + reviewContent + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
