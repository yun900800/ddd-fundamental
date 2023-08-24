package org.ddd.fundamental.geteway.controller;

import org.ddd.fundamental.geteway.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserGatewayController {

    @Autowired
    private UserService userService;

    @RequestMapping("/user-info")
    public String userInfo() {
        return userService.userInfo();
    }

    @RequestMapping("/user-info/{userId}")
    public String userInfoById(@PathVariable String userId) {
        return userService.userInfo(userId);
    }

}
