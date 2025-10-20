package com.lee.basicspring.controller;

import com.lee.basicspring.data.dto.JoinRequest;
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

    /* 
     * localhost:8080 요청에 대한 처리 home controller
     * 조작 view loginType, pageName
     * 존재하는 memberId가 있을 경우 nickname을 출력
     */
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

    /* 
     * 회원가입 페이지, 회원 가입 시 전달되는 data JoinRequest dto에 담아서 전달.
     */
    @GetMapping("/join")
    public String joinPage(Model model) {
        
        model.addAttribute("loginType", "cookie-login");
        model.addAttribute("pageName", "쿠키 로그인");

        model.addAttribute("joinRequest",new JoinRequest());

        return "join";
    }
    
    
    
}
