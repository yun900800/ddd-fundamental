package org.ddd.fundamental.spring.configurablelistable.controller;

import org.ddd.fundamental.spring.configurablelistable.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;

public class MyController {

    @Autowired
    private MyService myService;
}
