package org.ddd.fundamental.app.controller;

import org.ddd.fundamental.app.api.user.UserRequest;
import org.ddd.fundamental.app.service.beachmark.BeachMarkService;
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

    @RestController
    public class BeachMarkController {
        @Autowired
        private BeachMarkService beachMarkService;

        @GetMapping(value = "/connection-per-channel/{workerCount}/{iterations}/{payloadSize}")
        public void connectionPerChannel(@PathVariable("workerCount") int workerCount,
                                         @PathVariable("iterations")int iterations,
                                         @PathVariable("payloadSize")int payloadSize ) {
            beachMarkService.connectionPerChannel(workerCount,iterations,payloadSize);
        }

        @GetMapping(value = "/connection-shared/{workerCount}/{iterations}/{payloadSize}")
        public void sharedConnection(@PathVariable("workerCount") int workerCount,
                                         @PathVariable("iterations")int iterations,
                                         @PathVariable("payloadSize")int payloadSize ) {
            beachMarkService.sharedConnection(workerCount,iterations,payloadSize);
        }

        @GetMapping(value = "/connection-single/{workerCount}/{iterations}/{payloadSize}")
        public void singleConnection(@PathVariable("workerCount") int workerCount,
                                     @PathVariable("iterations")int iterations,
                                     @PathVariable("payloadSize")int payloadSize ) {
            beachMarkService.singleConnection(workerCount,iterations,payloadSize);
        }

        @GetMapping(value = "/connection-single-nio/{workerCount}/{iterations}/{payloadSize}")
        public void singleConnectionNio(@PathVariable("workerCount") int workerCount,
                                     @PathVariable("iterations")int iterations,
                                     @PathVariable("payloadSize")int payloadSize ) {
            beachMarkService.singleConnectionNio(workerCount,iterations,payloadSize);
        }
    }
}
