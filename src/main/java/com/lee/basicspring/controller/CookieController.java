package com.lee.basicspring.controller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lee.basicspring.data.dto.JoinRequest;
import com.lee.basicspring.data.dto.LoginRequest;
import com.lee.basicspring.data.entity.Member;
import com.lee.basicspring.service.MemberServiceimpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;






@Controller
@RequiredArgsConstructor
@RequestMapping("/cookie-login")
public class CookieController {

    private final MemberServiceimpl memberServiceimpl;
    private final Logger LOGGER = LoggerFactory.getLogger((CookieController.class));

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
        LOGGER.info("=========================");
        
        return "home";
    }

    /* 
     * 회원 가입 페이지 get 요청, 회원 가입 시 전달되는 data JoinRequest dto에 담아서 전달.
     */
    @GetMapping("/join")
    public String joinPage(Model model) {
        
        model.addAttribute("loginType", "cookie-login");
        model.addAttribute("pageName", "쿠키 로그인");
        
        model.addAttribute("joinRequest",new JoinRequest());

        return "join";
    }
    
    /* 
     * 회원 가입 요청, dto에 담아서 post 요청
    * Check if the loginId already exists (duplicate)
    * Check if the nickName already exists (duplicate)
    * Check if password and passwordCheck are the same

    * If there are any errors, return "join".
    * Otherwise, redirect to "/cookie-login".
     */
    @PostMapping("/join")
    public String join(@Valid @ModelAttribute JoinRequest joinRequest, BindingResult bindingResult, Model model) {
        model.addAttribute("loginType", "cookie-login");
        model.addAttribute("pageName", "쿠키 로그인");
        LOGGER.info("join post mapping 전역 log");

        //loingId duplication check | 여기서 get으로 꺼내려고할 때 값이 만약에 없으면 NPE 뜨니까 이 해결 방법 고안
        if(memberServiceimpl.checkLoginIdDuplicate(joinRequest.getLoginId())){
            bindingResult.addError(new FieldError("joinRequest", "loginId", "로그인 아이디가 중복됩니다."));
        }


        //nickName duplication check | 여기서 get으로 꺼내려고할 때 값이 만약에 없으면 NPE 뜨니까 이 해결 방법 고안
        if(memberServiceimpl.checkNicknameDuplicate(joinRequest.getNickname())){
            bindingResult.addError(new FieldError("joinRequest", "nickname", "닉네임이 중복됩니다."));
        }

        //password와 passwordCheck가 같은지 확인
        if(!joinRequest.getPassword().equals(joinRequest.getPasswordCheck())){
            bindingResult.addError(new FieldError("joinRequest", "passwordCheck", "비밀번호가 일치하지 않습니다."));
        }

        if(bindingResult.hasErrors()){
            return "join";
        }

        memberServiceimpl.join(joinRequest);
        return "redirect:/cookie-login";
    }
    
    /* 
     * Request login page Get Mapping
     * return login.html
     */
    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("loginType", "cookie-login");
        model.addAttribute("pageName", "쿠키 로그인");

        model.addAttribute("loginRequest", new LoginRequest());
        
        return "login";
    }
    
    
    
    
}
