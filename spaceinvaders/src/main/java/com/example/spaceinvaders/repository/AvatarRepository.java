package com.example.spaceinvaders.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.spaceinvaders.model.Avatar;
import com.example.spaceinvaders.model.Avatar;


@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    
    List<Avatar> findAllByNombre(String text);

    List<Avatar> findAllByNombreStartingWithIgnoreCase(String text);

    List<Avatar> findAllByNombreEndingWithIgnoreCase(String text);

    List<Avatar> findAllByNombreContainingIgnoreCase(String text);
}
