package org.ddd.fundamental.app.service.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ddd.fundamental.app.model.PostReview;
import org.ddd.fundamental.app.repository.user.PostReviewRepository;
import org.ddd.fundamental.share.domain.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostReviewService {

    private static  final Logger LOGGER = LoggerFactory.getLogger(PostReviewService.class);

    @Autowired
    private PostReviewRepository postReviewRepository;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Transactional(transactionManager = "transactionManager")
    public Long  createPostReview(long postId,long userId, String content) {
        PostReview postReview = PostReview.newPostReview(userId, postId, content);
        postReviewRepository.save(postReview);
        return postReview.getId();
    }

    public void createBigRedisData(long userId) {
        List<PostReview> postReviewList = postReviewRepository.findByUserId(userId);
        ObjectMapper mapper = new ObjectMapper();
        try {
            String result = mapper.writeValueAsString(postReviewList);
            stringRedisTemplate.opsForValue().set(""+userId, result);
        } catch (Exception e){
            LOGGER.error("parse userId:{} json data error:{}", userId,e);
            throw new RuntimeException("userId:"+ userId);
        }
    }

    public void splitBigRedisData(Long userId) {
        List<PostReview> postReviewList = postReviewRepository.findByUserId(userId);
        ObjectMapper mapper = new ObjectMapper();
        for (PostReview postReview: postReviewList) {
            long postId = postReview.getPostId();
            long id = postReview.getId();
            String key = userId + "_" + postId +"_" + id;
            try {
                String result = mapper.writeValueAsString(postReview);
                stringRedisTemplate.opsForValue().set(key, result);
            } catch (Exception e){
                LOGGER.error("parse userId:{}, postId:{}, id:{} json data error:{}", userId,postId, id,e);
                throw new RuntimeException("userId:"+ userId + ",postId:" +postId+ ",id:"+id);
            }
        }
    }

    public String accessManyTimesCost(String key,int times) {
        long start = System.currentTimeMillis();
        while (times-- >=0){
            stringRedisTemplate.opsForValue().get(key);
        }
        long costTime  = System.currentTimeMillis() - start;
        String time = costTime / 1000.0 + "s";
        LOGGER.info("cost time:{}", time);
        return time;
    }
}
