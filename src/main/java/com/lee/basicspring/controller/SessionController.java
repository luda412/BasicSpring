package com.lee.basicspring.controller;

import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.lee.basicspring.data.dto.JoinRequest;
import com.lee.basicspring.data.entity.Member;
import com.lee.basicspring.service.MemberServiceimpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;




@Controller
@RequiredArgsConstructor
@RequestMapping("/session-login")
public class SessionController {
    private final MemberServiceimpl memberServiceimpl;

    /* 
     * home page get request
     * Get the memberId stored in the session
     */
    @GetMapping(value={"", "/"})
    public String home(Model model, @SessionAttribute(name="memberId", required=false) Long memberId) {

        Member loginMember = memberServiceimpl.getLoginMemberById(memberId);

        if(loginMember != null){
            model.addAttribute("nickname", loginMember.getNickname());
        }
        return "home";
    }

    /* 
     * join page
     * JoinRequestdto to receive input from the member
     */
    @GetMapping("/join")
    public String joinPage(Model model) {
        model.addAttribute("joinRequest", new JoinRequest());
        return "join";
    }
    
    /* 
     * 
     */
    @PostMapping("/join")
    public String join(@Valid @ModelAttribute JoinRequest joinRequest, BindingResult bindingResult, Model model) {
        
        //loginId 중복 체크
        if()
        
        //닉네임 중복 체크


        // password와 passwordCheck가 같은지 체크

    }
    
    

}
