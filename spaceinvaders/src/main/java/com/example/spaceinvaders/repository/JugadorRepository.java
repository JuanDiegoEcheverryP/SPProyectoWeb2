package com.example.spaceinvaders.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.spaceinvaders.model.Jugador;


@Repository
public interface JugadorRepository extends JpaRepository<Jugador, Long> {

    @Query("SELECT n FROM Jugador n WHERE n.avatar.id = :idAvatar")
    List<Jugador>findJugadoresByAvatarId(@Param("idAvatar") Long idAvatar);

    @Query("SELECT n FROM Jugador n WHERE n.naveJuego.id = :idNave")
    List<Jugador>findJugadoresByNaveId(@Param("idNave") Long idNave);

    List<Jugador> findAllByNombre(String text);

    List<Jugador> findAllByNombreStartingWithIgnoreCase(String text);

    List<Jugador> findAllByNombreEndingWithIgnoreCase(String text);

    List<Jugador> findAllByNombreContainingIgnoreCase(String text);
}

