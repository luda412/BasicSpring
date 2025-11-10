package com.lee.basicspring.controller;

import com.lee.basicspring.data.dto.JoinRequest;
import com.lee.basicspring.data.dto.LoginRequest;
import com.lee.basicspring.data.entity.Member;
import com.lee.basicspring.service.MemberServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/security-login")
public class SecurityContorller {

    private final MemberServiceImpl memberServiceImpl;

    //home에 대한 요청
    @GetMapping(value = {"", "/"})
    public String home(Model model, Authentication auth){
    /*
    * auth 객체 안에 들어있는 값
    * principal | 로그인한 사용자 객체 (principalDetails)
    * credentials | 비밀번호 (또는 인증 수단)
    * authorities | 권한(Role) 목록
    * name | username (getUsername())
    * */
        if(auth != null){
            Member loginMember = memberServiceImpl.getLoginMemberByLoginId(auth.getName());
            if(loginMember != null){
                model.addAttribute("nickname", loginMember.getNickname());
            }
        }
        return "home";
    }

    @GetMapping("/join")
    public String joinPage(Model model){
        model.addAttribute("joinRequest", new JoinRequest());
        return "join";
    }

    @PostMapping("/join")
    public String join(@Valid @ModelAttribute JoinRequest joinRequest, BindingResult bindingResult, Model model){
        //loginId 중복체크
        if(memberServiceImpl.checkLoginIdDuplicate(joinRequest.getLoginId())){
            bindingResult.addError(new FieldError("joinRequest", "loginId", "로그인 아이디가 중복됩니다."));
        }
        //닉네임 중복체크
        if(memberServiceImpl.checkNicknameDuplicate(joinRequest.getNickname())){
            bindingResult.addError(new FieldError("joinRequest", "nickname", "닉네임이 중복됩니다."));
        }
        //password와 passwordCheck가 같은지 확인
        if(!joinRequest.getPassword().equals(joinRequest.getPasswordCheck())){
            bindingResult.addError(new FieldError("joinRequest", "passwordCheck", "비밀번호가 일치하지 않습니다."));
        }
        if(bindingResult.hasErrors()){
            return "join";
        }

        //Bcrypt 알고리즘을 사용한 암호화 비밀번호로 저장
        memberServiceImpl.joinEncoder(joinRequest);
        return "redirect:/security-login";
    }

    @GetMapping("/login")
    public String loginPage(Model model){
        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
    }

}
