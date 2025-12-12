package com.lee.basicspring.data.entity;

import com.lee.basicspring.data.entity.type.MemberRole;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity //테이를 설계 -> 필드 변수들을 기반으로 데이터 컬럼 구성
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private String loginId;
    private String password;
    private String nickname;

    private MemberRole role;

    // For OAuth google login
    private String provider;
    private String providerId;
}
