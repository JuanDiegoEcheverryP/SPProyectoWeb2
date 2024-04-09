package com.example.spaceinvaders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.spaceinvaders.model.Camino;
import com.example.spaceinvaders.model.Estrella;

import java.util.List;
import java.util.Optional;


@Repository
public interface CaminoRepository extends JpaRepository<Camino, Long> {
   
   Optional<Camino> findByEstrellaInicioAndEstrellaFinal(Estrella estrellaInicio, Estrella estrellaFinal);
    
    @Query("SELECT c.estrellaInicio FROM Camino c WHERE c.id = :idCamino")
    Estrella findEstrellaByInicioId(@Param("idCamino") Long idCamino);

    @Query("SELECT c FROM Camino c WHERE c.estrellaInicio.id = :idEstrella")
    List<Camino> findCaminosPorEstrellaInicio(@Param("idEstrella") Long idEstrella);

    @Query("SELECT c.estrellaFinal FROM Camino c WHERE c.estrellaInicio.id = :idEstrella")
    List<Estrella> obtenerEstrellaFinalPorEstrellaInicioId(@Param("idEstrella") Long idEstrella);

    //@Query("SELECT c FROM Camino c WHERE c.estrellaInicio.id = :idInicio AND c.estrellaFinal.id = idFinal")
    //Camino obtenerCaminoDosEstrellas(@Param("idInicio") Long idInicio,@Param("idFinal") Long idFinal);
}

