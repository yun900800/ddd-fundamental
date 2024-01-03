package org.ddd.fundamental.app.controller;

import org.ddd.fundamental.app.api.user.PostReviewRequest;
import org.ddd.fundamental.app.service.post.PostReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostReviewController {

    @Autowired
    private PostReviewService postReviewService;

    @PostMapping(("{userId}/post/{postId}"))
    public long createPostReview(@PathVariable long userId, @PathVariable long postId,
                                 @RequestBody PostReviewRequest reviewRequest) {
        return postReviewService.createPostReview(postId,userId,reviewRequest.getReviewContent());
    }

    @PostMapping(("{userId}/big-redis-data/create"))
    public void createBigRedisData(@PathVariable long userId) {
        postReviewService.createBigRedisData(userId);
    }

    @GetMapping(("{key}/many-times/{times}"))
    public String accessManyTimesCost(@PathVariable String key, @PathVariable int times) {
        return postReviewService.accessManyTimesCost(key,times);
    }

    @PostMapping(("{userId}/big-redis-data/split"))
    public void splitBigRedisData(@PathVariable long userId) {
        postReviewService.splitBigRedisData(userId);
    }
}
