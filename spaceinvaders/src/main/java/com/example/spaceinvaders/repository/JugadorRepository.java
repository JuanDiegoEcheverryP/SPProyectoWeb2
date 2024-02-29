package com.example.spaceinvaders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.spaceinvaders.model.Jugador;


@Repository
public interface JugadorRepository extends JpaRepository<Jugador, Long> {
    
}

