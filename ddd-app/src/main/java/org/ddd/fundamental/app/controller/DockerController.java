package org.ddd.fundamental.app.controller;

import org.apache.commons.lang3.StringUtils;
import org.ddd.fundamental.app.api.user.UserRequest;
import org.ddd.fundamental.app.exception.MyCustomException;
import org.ddd.fundamental.app.service.beachmark.BeachMarkService;
import org.ddd.fundamental.app.service.product.ProductService;
import org.ddd.fundamental.app.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class DockerController {

    private  static final Logger LOGGER = LoggerFactory.getLogger(DockerController.class);

    @Value("${server.port}")
    private String port;

    @RestController
    public class DockerMessageController {
        @GetMapping(value = "/messages")
        public String getMessage(HttpServletRequest req, HttpServletResponse res) {
            String str = req.getHeader("security");
            if (StringUtils.isEmpty(str)) {
                return "Hello from Docker! no secure access, this server port is:" + port +".";
            } else {
                return "Hello from Docker! secure access, this server port is:" + port +".";
            }
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

        @PostMapping(value = "/test-multiple-data-source")
        public void testMultiPleDataSource(@RequestBody UserRequest request) {
            userService.saveUserModel(request.getUserName(),
                    request.getPassword());
            userService.saveOrder(request.getUserName());
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
    public class ErrorUserController {
        @Autowired
        private UserService userService;
        @GetMapping(value = "/error-register")
        public void registerUser() {
            userService.errorRegisterUser();
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

    @RestController
    public class ProductController {

        @Autowired
        private ProductService productService;
        @PostMapping(value = "/snatchProduct/{productId}")
        public void snatchProduct(@PathVariable("productId") String productId) {
            productService.snatchProduct(productId);
        }

        @PostMapping(value = "/snatchProduct")
        public void batchSnatchProduct() {
            productService.initProductCount();
            productService.batchSnatchProduct();
        }

    }

    @RestController
    public class ErrorOrExceptionController {

        @ExceptionHandler({ MyCustomException.class})
        public void handleException() {
            LOGGER.info("this is a exception handler for a special controller");
        }

        @GetMapping(value = "/exception")
        public void exceptionRequest() {
            throw new IllegalArgumentException();
            //throw new MyCustomException("error request");
        }

        @GetMapping(value = "/exception1")
        public void exceptionRequest1() {
            try {
                int ret = 10 / 0;
            }catch (RuntimeException e){
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "除数不能为0呀，兄弟", e);
            }
            //throw new MyCustomException("error request");
        }

    }
}
