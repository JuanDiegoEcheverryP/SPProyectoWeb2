package com.example.spaceinvaders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.spaceinvaders.model.Camino;
import com.example.spaceinvaders.model.Estrella;
import java.util.Optional;


@Repository
public interface CaminoRepository extends JpaRepository<Camino, Long> {
   
   Optional<Camino> findByEstrellaInicioAndEstrellaFinal(Estrella estrellaInicio, Estrella estrellaFinal);
    
    @Query("SELECT c.estrellaInicio FROM Camino c WHERE c.id = :idCamino")
    Estrella findEstrellaByInicioId(@Param("idCamino") Long idCamino);

}

