package org.ddd.fundamental.security.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    private static Logger LOGGER = LoggerFactory.getLogger(SecurityController.class);

    @GetMapping("")
    public String home() {
        LOGGER.info("this is home");
        return "hello welcome home page";
    }

    @GetMapping("security/authenticate")
    public String accessNeedAuthenticated() {
        LOGGER.info("this is a test with userName and password");
        return "need authenticate";
    }

    @GetMapping("security/customer")
    public String customerCanAccess() {
        LOGGER.info("this interface can be accessed by all roles");
        return "welcome new customer";
    }

    @GetMapping("security/call-center")
    public String callCenterCanAccess() {
        LOGGER.info("this interface can be accessed by call-center or admin roles");
        return "welcome to call-center";
    }

    @GetMapping("security/admin")
    public String adminCanAccess() {
        LOGGER.info("this interface only can be  accessed by admin roles");
        return "welcome to admin dashboard";
    }

}
