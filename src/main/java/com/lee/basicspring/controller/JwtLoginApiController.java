package com.lee.basicspring.controller;

import com.lee.basicspring.data.dto.JoinRequest;
import com.lee.basicspring.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jwt-login")
public class JwtLoginApiController {
    private final MemberServiceImpl memberServiceImpl;

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

}
