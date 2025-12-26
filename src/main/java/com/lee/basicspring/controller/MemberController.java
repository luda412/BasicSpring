package com.lee.basicspring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lee.basicspring.service.interfaces.MemberService;

import lombok.RequiredArgsConstructor;



@RestController
@RequiredArgsConstructor
public class MemberController {
    
    private final MemberService memberService;

    @GetMapping("/hello")
    public String getHello() {
        return "Hello world!";
    }

    
    // @PostMapping("/join")
    // public String join(@RequestBody JoinRequest joinRequest) {

    //     String result = memberService.join(joinRequest);

    //     if("success".equalsIgnoreCase(result)){ //""문자열을 메서드 앞으로 빼는 이유는 result에 null 담겨서 메서드가 호출되지 않는 NPE를 사전에 방지 하기 위해서 result가 null 담겨도 NPE 발생이 아닌 else문을 타게 하려고
    //         return "success";
    //     }else{
    //         return "fail";
    //     }
    // }
    
}
