package com.lee.basicspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lee.basicspring.data.entity.Pet;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long>{
    
}
