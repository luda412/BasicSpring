package com.lee.basicspring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class MemberController {
    
    @GetMapping("/hello")
    public String getHello() {
        return "Hello world!";
    }
    @PostMapping("/join")
    public String join(@RequestBody String entity) {
        
        
        return entity;
    }
    
}
