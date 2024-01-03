package org.ddd.fundamental.geteway.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.ddd.fundamental.geteway.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private static final String URL = "http://spring-cloud-module-user";

    private Map<String,String> userInfo = new HashMap<>();

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "defaultUserInfo")
    public String userInfo() {
        return this.restTemplate.getForObject(
                URL+"/greeting", String.class);
    }

    @HystrixCommand(
            commandKey = "userByIdFromDB",
            fallbackMethod = "getUserFromCache")
    public String userInfo(String userId) {
        try {
//            if (userInfo.containsKey(userId)) {
//                LOGGER.info("from cache data is {}",userId);
//                return userInfo.get(userId);
//            }
            String user = this.restTemplate.getForObject(URL+"/user/info/"+ userId, String.class);
            LOGGER.info("from remote data is {}",user);
            if (!userInfo.containsKey(userId) && null != user) {
                userInfo.put(userId, user);
            }
            if (null == user) {
                throw new UserNotFoundException("数据不存在呀");
            }
            return user;
        } catch (RuntimeException e){
            throw new UserNotFoundException(e.getMessage());
        }

    }

    private String defaultUserInfo() {
        return "default hystrix user";
    }

    private String getUserFromCache(String userId) {
        LOGGER.info("from cache data is {}",userId);
        if (userInfo.containsKey(userId)) {
            LOGGER.info("cache data is {}",userInfo);
            return userInfo.get(userId);
        }
        return "还是返回一个数据不存在的值算啦";
    }
}
