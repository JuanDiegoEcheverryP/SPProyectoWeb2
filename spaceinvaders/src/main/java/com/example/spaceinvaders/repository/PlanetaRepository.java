package com.example.spaceinvaders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.spaceinvaders.model.Planeta;


@Repository
public interface PlanetaRepository extends JpaRepository<Planeta, Long> {
    
}

