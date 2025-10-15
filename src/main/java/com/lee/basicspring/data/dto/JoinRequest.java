package com.lee.basicspring.data.dto;


import com.lee.basicspring.data.entity.Member;
import com.lee.basicspring.data.entity.type.MemberRole;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JoinRequest {
    
    @NotBlank(message= "로그인 아이디가 비어있습니다.")
    private String loginId;
    
    @NotBlank(message="비밀번호가 비어있습니다.")
    private String password;
    private String passwordCheck;

    @NotBlank(message="닉네임이 비어있습니다.")
    private String nickname;
    
    // 비밀번호 암호화 전
    public Member toEntity(){
        return Member.builder()
            .loginId(this.loginId)
            .password(this.password)
            .nickname(this.nickname)
            .role(MemberRole.USER)
            .build();
    }

    // 비밀 번호 암호화
    public Member toEntity(encodeedPassword){
        return Member.builder()
            .loginId(this.loginId)
            .password(encodeedPassword)
            .nickname(this.nickname)
            .role(MemberRole.USER)
            .build();
    }


}
