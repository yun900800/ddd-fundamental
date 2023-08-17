package org.ddd.fundamental.ribbon.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class LoadBalanceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoadBalanceController.class);

    private static final String URL = "http://users/messages";

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "ribbon")
    public String ribbon(HttpServletRequest req, HttpServletResponse res) {
        String ret =  this.restTemplate.getForObject(URL,String.class);
        LOGGER.info("response:{}",res);
        res.setHeader("Access-Control-Expose-Headers","key");
        res.setHeader("key","token");
        return ret;
    }
}
