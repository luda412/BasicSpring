package com.lee.basicspring.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.lee.basicspring.data.entity.Member;
import com.lee.basicspring.data.entity.type.MemberRole;
import com.lee.basicspring.repository.MemberRepository;
import com.lee.basicspring.security.auth.PrincipalDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalOAuth2UserService extends DefaultOAuth2UserService{

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;
    
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        
        //userRequest 안에는 clientId, secret, provider 이름 등이 들어있음.
        OAuth2User oAuth2User = super.loadUser(userRequest);
        //oAuth2User.getAttributes()는 제공자가 내려준 raw attribute map 객체(sub, name, email 등)
        log.info("getAttributes: {}", oAuth2User.getAttributes());

        String provider = userRequest.getClientRegistration().getRegistrationId();
        String providerId = oAuth2User.getAttribute("sub");
        String loginId = provider + "_" + providerId;

        Optional<Member> optionalMember = memberRepository.findByLoginId(loginId);

        Member member;
        
        if(optionalMember.isEmpty()){
            member = Member.builder()
                        .loginId(loginId)
                        .nickname(oAuth2User.getAttribute("name"))
                        .provider(provider)
                        .providerId(providerId)
                        .role(MemberRole.USER)
                        .build();
            memberRepository.save(member);
        }else{
            member = optionalMember.get();
        }
        return new PrincipalDetails(member, oAuth2User.getAttributes());
    }
}
