package com.example.spaceinvaders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.spaceinvaders.model.Estrella;


@Repository
public interface EstrellaRepository extends JpaRepository<Estrella, Long> {
    
}

