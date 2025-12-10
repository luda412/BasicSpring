package com.lee.basicspring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lee.basicspring.data.dto.JoinRequest;
import com.lee.basicspring.data.dto.LoginRequest;
import com.lee.basicspring.data.entity.Member;
import com.lee.basicspring.security.jwt.JwtTokenUtil;
import com.lee.basicspring.service.MemberServiceImpl;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/jwt-login")
public class JwtLoginApiController {
    private final MemberServiceImpl memberServiceImpl;
    private final Logger LOGGER = LoggerFactory.getLogger((JwtLoginApiController.class));
    
    @PostMapping("/join")
    public String join(@RequestBody JoinRequest joinRequest){

        // loginId 중복 체크
        if(memberServiceImpl.checkLoginIdDuplicate(joinRequest.getLoginId())){
            return "로그인 아이디가 중복 됩니다.";
        }

        // 닉네임 중복 체크
        if(memberServiceImpl.checkNicknameDuplicate(joinRequest.getNickname())){
            return "닉네임이 중복됩니다.";
        }

        //password와 passwordCheck가 같은지 확인
        if(!joinRequest.getPassword().equals(joinRequest.getPasswordCheck())){
            return "비밀번호가 일치하지 않습니다.";
        }

        memberServiceImpl.joinEncoder(joinRequest);
        return "회원가입 성공";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        Member member = memberServiceImpl.login(loginRequest);
        LOGGER.info("jwt login controller called =====================");
        //Case wrong login Id or password -> global error return
        if(member == null){
            return "로그인 아이디 또는 비밀번호가 틀렸습니다.";
        }

        //로그인 성공 -> Jwt Token 발급
        long expireTimeMs = 1000 * 60 * 60; // 1시간
        String jwtToken = JwtTokenUtil.createToken(member.getLoginId(), expireTimeMs);

        return jwtToken;
    }
    
    @GetMapping("/info")
    public String memberInfo(Authentication auth) {
        Member loginMember = memberServiceImpl.getLoginMemberByLoginId(auth.getName());

        return String.format("loginId: %s\n nickname: %s\n role: %s",
            loginMember.getLoginId(), loginMember.getNickname(), loginMember.getRole().name()
        );
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "관리자 페이지 접근 성공";
    }
    
    

}
