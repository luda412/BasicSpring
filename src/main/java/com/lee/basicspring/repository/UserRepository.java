package com.lee.basicspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lee.basicspring.data.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
    
}
