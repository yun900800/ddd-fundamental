package org.ddd.fundamental.user.controller;

import com.netflix.discovery.EurekaClient;
import org.ddd.fundamental.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;

@RestController
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    @Lazy
    private EurekaClient eurekaClient;

    @Value("${server.port}")
    private String port;

    @Value("${spring.application.name}")
    private String appName;

    @Autowired
    private UserService userService;

    @RequestMapping("/greeting")
    public String greeting() throws InterruptedException {
        Integer random = new Random().nextInt();
        if (random.intValue() % 3 == 0) {
            throw new RuntimeException("随机数是3的倍数,抛出异常");
        }

        String greet = String.format(
                "Hello from '%s'! access port is %s ", eurekaClient.getApplication(appName).getName(),
                port);
        LOGGER.info("nice to meet you, %s",eurekaClient.getApplication(appName).getName());
        userService.doSomeWorkSameSpan();
        userService.doSomeWorkNewSpan();
        userService.threadSpanTest();
        return greet;
    }

    private Map<String,String> userInfo = new HashMap<>();

    private List<String> productNames = new ArrayList<>();


    @PostConstruct
    public void init() {
        userInfo.put("1000","yun900800");
        userInfo.put("1001","yun900801");
        userInfo.put("1002","yun900802");
        productNames.add("橘子");
        productNames.add("香蕉");
        productNames.add("苹果");
        productNames.add("梨子");
    }

    @PreDestroy
    public void destroy() {
        userInfo.clear();
    }

    @RequestMapping("/user/info/{userId}")
    public String getUserInfo(@PathVariable String userId) {
        Integer random = new Random().nextInt(6);
        Integer index = new Random().nextInt(4);
        String name = productNames.get(index);
        Integer count = new Random().nextInt(20);
        userService.addItemByUserId(userId,name,count);
        LOGGER.info("userId is {}, random is {}", userId, random);
        if (random.intValue() % 3 != 0) {
            userService.addUserCount(userId , port, true);
        } else {
            userService.addUserCount(userId , port, false);
            throw new RuntimeException("随机数是3的倍数,抛出异常");
        }

        return userInfo.get(userId);
    }
}
