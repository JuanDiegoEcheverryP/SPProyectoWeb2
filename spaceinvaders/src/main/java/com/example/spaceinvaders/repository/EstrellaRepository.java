package com.example.spaceinvaders.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.spaceinvaders.model.Estrella;


@Repository
public interface EstrellaRepository extends JpaRepository<Estrella, Long> {
    
    List<Estrella> findAllByNombre(String text);

     List<Estrella> findAllByNombreStartingWithIgnoreCase(String text);

    List<Estrella> findAllByNombreEndingWithIgnoreCase(String text);

    List<Estrella> findAllByNombreContainingIgnoreCase(String text);

}

