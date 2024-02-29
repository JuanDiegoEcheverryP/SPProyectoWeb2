package com.example.spaceinvaders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.spaceinvaders.model.TipoNave;


@Repository
public interface TipoNaveRepository  extends JpaRepository<TipoNave, Long> {
    
}

