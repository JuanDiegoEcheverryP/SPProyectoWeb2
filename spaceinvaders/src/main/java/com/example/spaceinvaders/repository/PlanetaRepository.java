package com.example.spaceinvaders.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.spaceinvaders.model.Planeta;
import com.example.spaceinvaders.model.Nave;


@Repository
public interface PlanetaRepository extends JpaRepository<Planeta, Long> {

    @Query("SELECT n FROM Nave n WHERE n.localizacion.id = :idPlaneta")
    List<Nave> findNavesByPlanetaId(@Param("idPlaneta") Long idPlaneta);

    List<Planeta> findAllByNombre(String text);

    List<Planeta> findAllByNombreStartingWithIgnoreCase(String text);

    List<Planeta> findAllByNombreEndingWithIgnoreCase(String text);

    List<Planeta> findAllByNombreContainingIgnoreCase(String text);
    
    List<Planeta> findAllByHabitadoTrue();
}

