package com.lee.basicspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lee.basicspring.data.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
    
}
