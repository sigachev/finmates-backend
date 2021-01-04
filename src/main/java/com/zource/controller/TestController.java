package com.zource.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

        @GetMapping(value="/api/helloworld")
        public String helloWorld(){
            return "helloworld!";
        }
}
