package com.nishchay.controller;

import com.nishchay.service.HelloService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("/hello")
    public ResponseEntity<?> hello(@RequestParam(defaultValue = "World") String name) {
        String updatedName =  helloService.sayHello(name);
        return ResponseEntity.status(HttpStatus.OK)
                .body(updatedName);
    }
}
