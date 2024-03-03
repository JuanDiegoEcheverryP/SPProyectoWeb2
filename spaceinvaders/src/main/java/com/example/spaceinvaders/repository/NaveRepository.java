package com.example.spaceinvaders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.spaceinvaders.model.Nave;


@Repository
public interface NaveRepository extends JpaRepository<Nave, Long> {
    
}
