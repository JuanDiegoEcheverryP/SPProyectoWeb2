package com.example.spaceinvaders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.spaceinvaders.model.Avatar;


@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    
}
