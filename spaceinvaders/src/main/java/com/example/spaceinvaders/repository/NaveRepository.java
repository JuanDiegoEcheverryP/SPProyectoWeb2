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
public interface NaveRepository extends JpaRepository<Nave, Long> {

    @Query("SELECT n FROM Nave n WHERE n.tipoNave.id = :idTipoNave")
    List<Nave> findNaveByTipoNaveId(@Param("idTipoNave") Long idTipoNave);

    List<Nave> findAllByNombre(String text);

    List<Nave> findAllByNombreStartingWithIgnoreCase(String text);

    List<Nave> findAllByNombreEndingWithIgnoreCase(String text);

    List<Nave> findAllByNombreContainingIgnoreCase(String text);

    @Query("SELECT j FROM Jugador j WHERE j.naveJuego.id = :idNave")
    List<Jugador> findNavesByNaveId(@Param("idNave") Long idNave);

    @Query("SELECT n.credito FROM Nave n WHERE n.id = :naveId")
    Float findCreditoById(@Param("naveId") Long naveId);

    @Query("SELECT SUM(pb.vol) FROM ProductoBodega pb WHERE pb.nave.id = :naveId")
    Float sumVolByNaveId(@Param("naveId") Long naveId);

    @Query("SELECT p.volumen FROM Producto p WHERE p.id = :productoId")
    Float findVolumenByProductoId(@Param("productoId") Long productoId);

    @Query("SELECT n.credito FROM Nave n WHERE n.id = :naveId")
    Float findCapacidadById(@Param("naveId") Long naveId);

    @Query("SELECT n.tipoNave.volBodega FROM Nave n WHERE n.id = :idNave")
    Float findCapacidadBodegaByNaveId(@Param("idNave") Long idNave);

    @Modifying
    @Transactional
    @Query("UPDATE Nave n SET n.credito = n.credito - :nuevoCredito WHERE n.id = :idNave")
    int sumarCreditoNave(@Param("idNave") Long idNave, @Param("nuevoCredito") Float nuevoCredito);
}
