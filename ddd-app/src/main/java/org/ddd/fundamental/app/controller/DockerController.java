package org.ddd.fundamental.app.controller;

import org.ddd.fundamental.app.api.user.UserRequest;
import org.ddd.fundamental.app.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class DockerController {


    @RestController
    public class DockerMessageController {
        @GetMapping(value = "/messages")
        public String getMessage() {
            return "Hello from Docker!";
        }
    }

    @RestController
    public class DockerUserController {

        @Autowired
        private UserService userService;
        @PostMapping(value = "/register")
        public void registerUser(@RequestBody UserRequest request) {

            userService.registerUser(request.getUserName(),
                    request.getPassword());
        }
    }

    @RestController
    public class AsyncUserController{
        @Autowired
        private UserService userService;
        @GetMapping(value = "/async-register")
        public void registerUser() {
            userService.asyncRegisterUser();
        }
    }
}
