package com.lee.basicspring.service;

import org.springframework.stereotype.Service;

import com.lee.basicspring.controller.dto.JoinRequest;
import com.lee.basicspring.data.entity.Member;
import com.lee.basicspring.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceimpl implements MemberService{

    private final MemberRepository memberRepository;

    @Override
    public String join(JoinRequest joinRequest) {
        Member member = Member.builder()
                .id(joinRequest.getId())
                .name(joinRequest.getName())
                .phoneNumber(joinRequest.getPhoneNumber())
                .build();
        memberRepository.save(member);
        
        return "success";
    }


}
