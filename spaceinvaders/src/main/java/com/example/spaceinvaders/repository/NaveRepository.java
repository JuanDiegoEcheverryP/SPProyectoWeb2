package com.example.spaceinvaders.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.spaceinvaders.model.Nave;


@Repository
public interface NaveRepository extends JpaRepository<Nave, Long> {

    @Query("SELECT n FROM Nave n WHERE n.tipoNave.id = :idTipoNave")
    List<Nave> findNaveByTipoNaveId(@Param("idTipoNave") Long idTipoNave);

    List<Nave> findAllByNombre(String text);

    List<Nave> findAllByNombreStartingWithIgnoreCase(String text);

    List<Nave> findAllByNombreEndingWithIgnoreCase(String text);

    List<Nave> findAllByNombreContainingIgnoreCase(String text);
}
