package com.lee.basicspring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lee.basicspring.data.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{

    boolean existsByLoginId(String loginId);
    boolean existsByNickname(String nickname);
    Optional<Member> findByLoginId (String loginId);
    
}
