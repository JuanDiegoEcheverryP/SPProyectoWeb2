package com.example.spaceinvaders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.spaceinvaders.model.Camino;
import com.example.spaceinvaders.model.Estrella;
import java.util.Optional;


@Repository
public interface CaminoRepository extends JpaRepository<Camino, Long> {
    Optional<Camino> findByEstrellaInicioAndEstrellaFinal(Estrella estrellaInicio, Estrella estrellaFinal);
    
}

