package com.lee.basicspring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
    * 회원 가입 요청, dto에 담아서 post 요청
    * Check if the loginId already exists (duplicate)
    * Check if the nickName already exists (duplicate)
    * Check if password and passwordCheck are the same

    * If there are any errors, return "join".
    * Otherwise, redirect to "/sesion-login".
     */
    @PostMapping("/join")
    public String join(@Valid @ModelAttribute JoinRequest joinRequest, BindingResult bindingResult, Model model) {
        
        //loginId 중복 체크
        if(memberServiceimpl.checkLoginIdDuplicate(joinRequest.getLoginId())){
            bindingResult.addError(new FieldError("joinRequest","loginId", "로그인 아이디가 중복됩니다."));
        }
        
        //닉네임 중복 체크
        if(memberServiceimpl.checkNicknameDuplicate(joinRequest.getNickname())){
            bindingResult.addError(new FieldError("joinRequest","nickname", "닉네입이 중복됩니다."));
        }

        // password와 passwordCheck가 같은지 체크
        if(!joinRequest.getPassword().equals(joinRequest.getPasswordCheck())){
            bindingResult.addError(new FieldError("joinRequest","passwordCheck", "비밀번호가 일치하지 않습니다."));
        }
        //Error 존재 시 Join page 다시 return
        if(bindingResult.hasErrors()){
            return "join";
        }

        //service를 통해 입력받은 member 정보 DB에 저장후 session-login(즉, home) 으로 redirect
        memberServiceimpl.join(joinRequest);
        return "redirect:/session-login";

    }
    
    

}
