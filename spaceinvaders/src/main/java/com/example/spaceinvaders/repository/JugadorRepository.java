package com.example.spaceinvaders.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.spaceinvaders.model.Jugador;
import com.example.spaceinvaders.model.Nave;

import jakarta.transaction.Transactional;


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

    @Query("SELECT j FROM Jugador j WHERE j.nombre = :nombre AND j.contrasena = :contrasena")
    List<Jugador> findJugadorByContrasenAndNombre(@Param("nombre") String nombre, @Param("contrasena") String contrasena);

    @Query("SELECT n.naveJuego FROM Jugador n WHERE n.id = :idJugador")
    Nave findNavePorJugadorId(@Param("idJugador") Long idJugador);

    @Query("SELECT n FROM Nave n WHERE n.id = :idNave")
    Nave findNave(@Param("idNave") Long idNave);

    @Transactional
    @Modifying
    @Query("UPDATE Jugador j SET j.rol = :rol WHERE j.id = :jugadorId")
    int actualizarRol(@Param("rol") String rol, @Param("jugadorId") Long jugadorId);

    @Transactional
    @Modifying
    @Query("UPDATE Jugador j SET j.naveJuego = :nave WHERE j.id = :jugadorId")
    int actualizarNave(@Param("nave") Nave  nave, @Param("jugadorId") Long jugadorId);
}

