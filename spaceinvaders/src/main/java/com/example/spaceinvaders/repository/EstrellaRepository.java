package com.example.spaceinvaders.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.spaceinvaders.model.Estrella;
import com.example.spaceinvaders.model.Nave;
import com.example.spaceinvaders.model.Planeta;


@Repository
public interface EstrellaRepository extends JpaRepository<Estrella, Long> {
    
    List<Estrella> findAllByNombre(String text);

    List<Estrella> findAllByNombreStartingWithIgnoreCase(String text);

    List<Estrella> findAllByNombreEndingWithIgnoreCase(String text);

    List<Estrella> findAllByNombreContainingIgnoreCase(String text);

    @Query("SELECT e FROM Estrella e WHERE e.coord_x = :coordX AND e.coord_y = :coordY AND e.coord_z = :coordZ")
    List<Estrella> findByCoords(@Param("coordX") Float coordX, @Param("coordY") Float coordY, @Param("coordZ") Float coordZ);

    @Query("SELECT n FROM Nave n WHERE n.localizacion.id = :idEstrella")
    List<Nave> findNavesByEstrellaId(@Param("idEstrella") Long idEstrella);
}

