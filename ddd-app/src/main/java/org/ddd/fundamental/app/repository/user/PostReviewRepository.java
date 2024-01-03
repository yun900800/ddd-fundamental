package org.ddd.fundamental.app.repository.user;

import org.ddd.fundamental.app.model.PostReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("postReviewRepository")
public interface PostReviewRepository extends JpaRepository<PostReview,Long> {

    List<PostReview> findByUserId(Long userId);
}
