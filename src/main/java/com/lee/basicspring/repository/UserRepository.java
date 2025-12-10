package com.lee.basicspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lee.basicspring.data.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    
}
