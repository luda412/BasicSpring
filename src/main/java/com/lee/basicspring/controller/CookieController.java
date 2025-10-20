package com.lee.basicspring.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lee.basicspring.data.entity.Member;
import com.lee.basicspring.service.MemberServiceimpl;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/cookie-login")
public class CookieController {

    private final MemberServiceimpl memberServiceimpl;

    @GetMapping(value={"", "/"})
    public String home(@CookieValue(name="memberId", required=false) Long memberId, Model model) {
        
        model.addAttribute("loginType", "cookie-login");
        model.addAttribute("pageName", "쿠키 로그인");

        Member loginMember = memberServiceimpl.getLoginMemberById(memberId);

        if(loginMember != null){
            model.addAttribute("nickname", loginMember.getNickname());
            loginMember.getNickname();
        }
        
        return "home";
    }
    
    
}
