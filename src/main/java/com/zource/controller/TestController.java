package com.zource.controller;

import com.zource.config.jwt.JwtTokenProvider;
import com.zource.model.User;
import com.zource.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class TestController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/api/helloworld")
    public String helloWorld() {
        return "helloworld!";
    }


    @GetMapping(value = "/api/test")
    public String testWorld(Principal principal) {


        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) principal;
        User user = userService.findByUsername(authenticationToken.getName());
        user.setToken(jwtTokenProvider.generateToken(authenticationToken));

        return "helloworld!";

    }

    @GetMapping(value = "/username" )
    public String currentUserName(Principal principal) {
        return principal.getName();
    }


}
