package com.lee.basicspring.security.auth;

import com.lee.basicspring.data.entity.Member;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.oauth2.core.user.OAuth2User;

public class PrincipalDetails implements UserDetails, OAuth2User{
    private Member member;
    
    private Map<String, Object> attributes;


    public PrincipalDetails(Member member, Map<String, Object> attributes){
        this.member = member;
        this.attributes = attributes;
    }
    

    //권한 관련 작업을 하기 위한 role return
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collections = new ArrayList<>();
        collections.add(() -> {
            return member.getRole().name();
        });
        return collections;
    }

    //get Password 메서드
    @Override
    public String getPassword() {
        return member.getPassword();
    }

    //get Username 메서드 (생성한 member는 loginId 사용)
    @Override
    public String getUsername() {
        return member.getLoginId();
    }

    //계정이 만료 되었는지 (true: 만료 x)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //계정이 잠겼는지 (true: 잠기지 않음)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //비밀번호가 만료되었는지 (true: 만료x)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //계정이 활성화(사용가능)인지 (true: 활성화)
    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    // OAuth2User의 메서드 Override
    @Override
    public Map<String, Object> getAttributes() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }
}
