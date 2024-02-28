package com.proyecto.spaceinvaders.NaveEspacial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.spaceinvaders.NaveEspacial.model.Avatar;


@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    
}
